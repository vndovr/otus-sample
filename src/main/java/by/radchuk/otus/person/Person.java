package by.radchuk.otus.person;

import javax.persistence.Entity;
import javax.persistence.Table;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "person")
public class Person extends PanacheEntity {

  String firstName;

  String lastName;
}
