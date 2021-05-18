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
  String userId;

  String firstName;

  String lastName;

  String email;
  
  String city;
  
  String postalCode;
  
  String addressLine;
  
  String phone;

  @Version
  long version;
}
