package by.radchuk.otus.account;

import java.math.BigDecimal;
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
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class Account extends PanacheEntityBase {

  @Id
  String userId;

  BigDecimal amount;

  @Version
  long version;
}
