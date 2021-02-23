package by.radchuk.otus.auth;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends PanacheEntityBase {

  @Id
  String login;

  String salt;

  String password;
}
