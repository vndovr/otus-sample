package by.radchuk.otus.billing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends PanacheEntityBase {

  @Id
  String id;

  @Enumerated(EnumType.STRING)
  BillingStateMachine state;

  String creditAccount;

  String debitAccount;

  String orderId;

  BigDecimal amount;

  @Lob
  String event;

  @CreationTimestamp
  LocalDateTime createdAt;
}
