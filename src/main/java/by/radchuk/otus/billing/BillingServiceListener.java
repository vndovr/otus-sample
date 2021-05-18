package by.radchuk.otus.billing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import by.radchuk.otus.system.Hostname;
import io.smallrye.reactive.messaging.annotations.Blocking;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@Transactional
public class BillingServiceListener {

  @Inject
  Jsonb jsonb;

  @Inject
  BillingService billingService;

  @Hostname
  String hostname;

  @Inject
  BillingStateMachineContext billingStateMachineContext;

  @Inject
  @Channel("billing-sm-events-out")
  Emitter<String> billingSmEmitter;

  @Incoming("billing-events-in")
  @Blocking
  @SneakyThrows
  public void onBillingEventMessage(String event) {
    if (!StringUtils.containsIgnoreCase(hostname, "bill"))
      return;
    log.info("Got billing event: {}", event);
    InvoiceDto dto = jsonb.fromJson(event, InvoiceDto.class);
    billingSmEmitter.send(billingService.createTransaction(dto.getUserId(), "system",
        dto.getOrderId(), dto.getAmount(), event));
  }

  @Incoming("billing-sm-events-in")
  @Blocking
  @SneakyThrows
  public void onBillingStateMachineMessage(String event) {
    if (!StringUtils.containsIgnoreCase(hostname, "bill"))
      return;
    log.info("Got event to process transaction: {}", event);
    Transaction transaction = billingService.getTransaction(event);
    log.info("Transaction state: {}", transaction.getState().toString());
    BillingStateMachine billingStateMachine =
        transaction.getState().next(transaction, billingStateMachineContext);
    if (!billingStateMachine.equals(BillingStateMachine.FINAL)) {
      log.info("New transaction state: {}", billingStateMachine.toString());
      billingService.updateState(event, billingStateMachine);
      billingSmEmitter.send(event);
    } else {
      log.info("Processing completed for: {}", event);
    }
  }

}
