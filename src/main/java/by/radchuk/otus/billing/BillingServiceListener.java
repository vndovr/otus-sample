package by.radchuk.otus.billing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import by.radchuk.otus.billing.Transaction.State;
import by.radchuk.otus.order.Order;
import io.smallrye.reactive.messaging.annotations.Blocking;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@Transactional
public class BillingServiceListener {

  static String X_REQ_ID = "91cd5bb7-ec6b-4a41-8710-4bfcb62ceb10";
  static String ORDER_EVENT_ENTITY = Order.class.getName();

  @Inject
  Jsonb jsonb;

  @Inject
  BillingService billingService;

  @Inject
  @Channel("notification-events-out")
  Emitter<String> notificationEmitter;

  @Inject
  @Channel("order-events-out")
  Emitter<String> orderEmitter;

  @Inject
  @RestClient
  AccountClient accountClient;

  @Incoming("billing-events-in")
  @Blocking
  public void onMessage(String event) {
    log.info("Got billing event: {}", event);
    InvoiceDto dto = jsonb.fromJson(event, InvoiceDto.class);
    long transactionId = billingService.createTransaction(dto.getUserId(), "system",
        dto.getOrderId(), dto.getAmount());

    try {
      accountClient.transfer(dto.getUserId(), "system", dto.getAmount());
      billingService.updateState(transactionId, State.PAID);
      handleSuccessfull(dto);
    } catch (Exception e) {
      billingService.updateState(transactionId, State.FAIL);
      handleFailure(dto);
    }
  }

  private void handleFailure(InvoiceDto dto) {
    orderEmitter
        .send(Json.createObjectBuilder().add("xReqId", X_REQ_ID).add("externalId", dto.getOrderId())
            .add("entity", ORDER_EVENT_ENTITY).add("type", "order.event.fail").add("data", "{}")
            .add("userId", dto.getUserId()).build().toString());
    log.info("Send fauilure notification");
    notificationEmitter.send(Json.createObjectBuilder().add("userId", dto.getUserId())
        .add("subject", "You don't have enough money on your account").add("body", "<<BODY>>")
        .build().toString());
  }

  private void handleSuccessfull(InvoiceDto dto) {
    orderEmitter
        .send(Json.createObjectBuilder().add("xReqId", X_REQ_ID).add("externalId", dto.getOrderId())
            .add("entity", ORDER_EVENT_ENTITY).add("type", "order.event.paid").add("data", "{}")
            .add("userId", dto.getUserId()).build().toString());
    log.info("Send successfull notification");
    notificationEmitter.send(Json.createObjectBuilder().add("userId", dto.getUserId())
        .add("subject", "You order has been processed successfully").add("body", "<<BODY>>").build()
        .toString());
  }
}
