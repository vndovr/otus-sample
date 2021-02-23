package by.radchuk.otus.profile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_profile")
public class Profile extends PanacheEntityBase {

  @Id
  String login;

  String firstName;

  String lastName;

  String email;

  @Version
  long version;
}
