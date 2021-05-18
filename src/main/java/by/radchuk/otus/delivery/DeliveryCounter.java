package by.radchuk.otus.delivery;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "delivery_counter")
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCounter extends PanacheEntityBase {

  @Id
  LocalDate id;

  int counter;

  @Version
  long version;
}
