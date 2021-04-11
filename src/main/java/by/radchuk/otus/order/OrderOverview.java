package by.radchuk.otus.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import by.radchuk.otus.order.Order.State;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_overview")
@NoArgsConstructor
public class OrderOverview extends PanacheEntityBase {

  @Id
  private String id;

  private String userId;

  private LocalDateTime creationDate;

  private String description;

  @Enumerated(EnumType.STRING)
  private State state;

  private BigDecimal price;

  OrderOverview(String id, String userId) {
    super();
    this.userId = userId;
    this.id = id;
  }

  void adjust(Order order) {
    this.state = order.getState();
    this.creationDate = order.getCreationDate();
    this.description = order.getDescription();
    this.price = order.getItems() == null || order.getItems().isEmpty() ? BigDecimal.ZERO
        : order.getItems().stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, (x, y) -> x.add(y));
  }
}
