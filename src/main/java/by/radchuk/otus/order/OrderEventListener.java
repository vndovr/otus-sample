package by.radchuk.otus.order;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.bind.Jsonb;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import by.radchuk.otus.order.Order.State;
import by.radchuk.otus.system.Hostname;
import io.smallrye.reactive.messaging.annotations.Blocking;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@Transactional
public class OrderEventListener {

  @Inject
  Jsonb jsonb;

  @Inject
  OrderVisitors orderVisitors;

  @Inject
  @Channel("billing-events-out")
  Emitter<String> emitter;

  @Hostname
  String hostname;

  @Incoming("order-events-in")
  @Blocking
  public void onMessage(String event) {

    if (!StringUtils.containsIgnoreCase(hostname, "order"))
      return;

    log.info("Received string: {}", event);
    EventDto dto = jsonb.fromJson(event, EventDto.class);
    log.info("Received an event: {}", dto);

    Order order = Optional.ofNullable((Order) Order.findById(dto.getExternalId()))
        .orElse(new Order(dto.getExternalId(), dto.getUserId()));

    OrderOverview orderOverview =
        Optional.ofNullable((OrderOverview) OrderOverview.findById(dto.getExternalId()))
            .orElse(new OrderOverview(dto.getExternalId(), dto.getUserId()));

    order.apply(orderVisitors.from(dto));
    orderOverview.adjust(order);

    Order.persist(orderOverview);
    Order.persist(order);


    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
    order.getItems().stream().map(item -> Json.createObjectBuilder().add("itemId", item.getItemId())
        .add("quantity", item.getQuantity()).build()).forEach(jsonArrayBuilder::add);

    if (orderOverview.getState().equals(State.READY)) {
      emitter.send(Json.createObjectBuilder().add("orderId", orderOverview.getId())
          .add("userId", orderOverview.getUserId())
          .add("amount", orderOverview.getPrice().toString()).add("items", jsonArrayBuilder.build())
          .build().toString());
    }
  }

}
