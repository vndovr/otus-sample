package by.radchuk.otus.notification;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "notification")
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends PanacheEntity {

  String userId;

  String email;

  String subject;

  String body;

  @CreationTimestamp
  LocalDateTime createdAt;
}
