package by.radchuk.otus.order;

import java.math.BigDecimal;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PriceList {

  Map<String, String> items = Map.of("1", "Spinning reel Shimano Exage 2500", "2",
      "Spinning rod Shimano Olivio 2.1m", "3", "Sprinnig rod Shimano Technium 2.7m", "4",
      "Spinning reel Shimano Baitrunner 4000", "5", "Trout fishing box Flambeau");

  Map<String, BigDecimal> prices = Map.of("1", BigDecimal.valueOf(3099, 2), "2",
      BigDecimal.valueOf(2599, 2), "3", BigDecimal.valueOf(7599, 2), "4",
      BigDecimal.valueOf(12599, 2), "5", BigDecimal.valueOf(10000, 2));

  String getName(String itemId) {
    return items.get(itemId);
  }

  BigDecimal getPrice(String itemId) {
    return prices.get(itemId);
  }
}
