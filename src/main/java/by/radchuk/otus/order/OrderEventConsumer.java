package by.radchuk.otus.order;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import by.radchuk.otus.orderevent.EventDto;
import io.smallrye.reactive.messaging.annotations.Blocking;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@Transactional
public class OrderEventConsumer {

  @Inject
  Jsonb jsonb;

  @Inject
  OrderVisitors orderVisitors;

  @Incoming("order-events-in")
  @Blocking
  public void onMessage(String event) {

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
  }

}
