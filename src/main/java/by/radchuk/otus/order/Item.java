package by.radchuk.otus.order;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Embeddable
public class Item extends PanacheEntityBase {

  @Column(name = "itemid")
  String itemId;

  @Column(name = "itemname")
  String itemName;

  @Column(name = "quantity")
  int quantity;

  @Column(name = "price")
  BigDecimal price;
}
