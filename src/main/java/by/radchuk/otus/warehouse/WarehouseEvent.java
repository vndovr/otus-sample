package by.radchuk.otus.warehouse;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "warehouse_event")
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseEvent extends PanacheEntityBase {

  @Id
  String id;

  @CreationTimestamp
  LocalDateTime createdAt;
}
