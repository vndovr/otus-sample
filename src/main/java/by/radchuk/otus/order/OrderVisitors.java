package by.radchuk.otus.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import org.apache.commons.lang3.StringUtils;
import by.radchuk.otus.order.Order.State;
import by.radchuk.otus.orderevent.EventDto;
import by.radchuk.otus.system.jaxrs.UnknownEventException;
import lombok.Setter;
import lombok.SneakyThrows;

@ApplicationScoped
public class OrderVisitors {

  @Inject
  Jsonb jsonb;

  @Inject
  PriceList priceList;

  Map<String, Class<? extends OrderVisitor>> css = Map.of("order.event.create",
      CreateOrderVisitor.class, "order.event.cancel", CancelOrderVisitor.class, "order.event.amend",
      AmendOrderVisitor.class, "order.event.ready", ReadyForBillingOrderVisitor.class);

  @SneakyThrows
  OrderVisitor from(EventDto dto) {
    OrderVisitor orderVisitor = Optional.ofNullable(css.get(dto.getType()))
        .map(clazz -> jsonb.fromJson(dto.getData(), clazz)).orElseThrow(UnknownEventException::new);
    if (orderVisitor instanceof AmendOrderVisitor) {
      ((AmendOrderVisitor) orderVisitor).setPriceList(priceList);
    }
    return orderVisitor;
  }


  public static class CreateOrderVisitor implements OrderVisitor {
    @Override
    public void visit(Order o) {
      o.setCreationDate(LocalDateTime.now());
      o.setDescription("");
      o.setItems(Collections.emptyList());
      o.setState(State.NEW);
    }
  }

  public static class CancelOrderVisitor implements OrderVisitor {
    @Override
    public void visit(Order o) {
      if (o.getState().equals(State.NEW) || o.getState().equals(State.READY)) {
        o.setState(State.CANCELED);
      }
    }
  }

  public static class AmendOrderVisitor implements OrderVisitor {

    @Setter
    String itemId;

    @Setter
    int quantity;

    @Setter
    PriceList priceList;

    @Override
    public void visit(Order o) {
      if (!o.getState().equals(State.NEW)) {
        return;
      }
      if (o.getItems() == null) {
        o.setItems(new ArrayList<>());
      }
      List<Item> items = o.getItems();
      for (int i = 0; i < items.size(); i++) {
        if (StringUtils.equals(items.get(i).getItemId(), itemId)) {
          items.get(i).setQuantity(items.get(i).getQuantity() + quantity);
          if (items.get(i).getQuantity() <= 0) {
            o.getItems().remove(i);
          }
          return;
        }
      }
      if (quantity > 0) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setQuantity(quantity);
        item.setItemName(priceList.getName(itemId));
        item.setPrice(priceList.getPrice(itemId));
        items.add(item);
      }
    }
  }

  public static class ReadyForBillingOrderVisitor implements OrderVisitor {
    @Setter
    String description;

    @Override
    public void visit(Order o) {
      if (o.getState().equals(State.NEW)) {
        o.setDescription(description);
        o.setState(State.READY);
      }
    }
  }
}
