package by.radchuk.otus.billing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import by.radchuk.otus.order.Order;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class NotificationClient {

  static String X_REQ_ID = "91cd5bb7-ec6b-4a41-8710-4bfcb62ceb10";
  static String ORDER_EVENT_ENTITY = Order.class.getName();

  @Inject
  @Channel("notification-events-out")
  Emitter<String> notificationEmitter;

  @Inject
  @Channel("order-events-out")
  Emitter<String> orderEmitter;

  void handleFailure(InvoiceDto dto) {
    orderEmitter
        .send(Json.createObjectBuilder().add("xReqId", X_REQ_ID).add("externalId", dto.getOrderId())
            .add("entity", ORDER_EVENT_ENTITY).add("type", "order.event.fail").add("data", "{}")
            .add("userId", dto.getUserId()).build().toString());
    log.info("Send fauilure notification");
    notificationEmitter.send(Json.createObjectBuilder().add("userId", dto.getUserId())
        .add("subject", "You don't have enough money on your account").add("body", "<<BODY>>")
        .build().toString());
  }

  void handleSuccessfull(InvoiceDto dto) {
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
