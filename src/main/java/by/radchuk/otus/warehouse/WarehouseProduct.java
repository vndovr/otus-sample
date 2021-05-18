package by.radchuk.otus.warehouse;

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
@Table(name = "warehouse_product")
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseProduct extends PanacheEntityBase {

  @Id
  String id;

  String name;

  long available;

  long reserved;

  @Version
  long version;
}
