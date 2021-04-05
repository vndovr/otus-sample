package by.radchuk.otus.order;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_info")
@NoArgsConstructor
public class Order extends PanacheEntityBase {

  @Id
  private String id;

  enum State {
    NEW, READY, BILLED, WAITING, SHIPPED, COMPLETED, CANCELED
  }

  private String userId;

  private LocalDateTime creationDate;

  private String description;

  @Enumerated(EnumType.STRING)
  private State state;

  @ElementCollection
  @CollectionTable(name = "order_item", joinColumns = @JoinColumn(name = "orderid"))
  private List<Item> items;

  Order(String id, String userId) {
    super();
    this.userId = userId;
    this.id = id;
  }

  void apply(OrderVisitor orderVisitor) {
    orderVisitor.visit(this);
  }
}
