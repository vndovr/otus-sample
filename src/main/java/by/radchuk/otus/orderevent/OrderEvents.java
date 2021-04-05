package by.radchuk.otus.orderevent;

import lombok.Getter;

public class OrderEvents {

  public static EventHolder createEvent() {
    return new EventHolder() {

      @Override
      public String typeid() {
        return "order.event.create";
      }
    };
  }

  public static EventHolder cancelEvent() {
    return new EventHolder() {

      @Override
      public String typeid() {
        return "order.event.cancel";
      }
    };
  }

  public static EventHolder amendEvent(String _itemId, int _quantity) {
    return new EventHolder() {
      @Getter
      String itemId = _itemId;

      @Getter
      int quantity = _quantity;

      @Override
      public String typeid() {
        return "order.event.amend";
      }
    };
  }

  public static EventHolder sendToBilling(String _description) {
    return new EventHolder() {

      @Getter
      String description = _description;

      @Override
      public String typeid() {
        return "order.event.ready";
      }
    };
  }
}
