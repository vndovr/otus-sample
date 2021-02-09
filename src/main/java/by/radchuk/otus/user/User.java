package by.radchuk.otus.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_info")
public class User extends PanacheEntity {

  String login;

  String firstName;

  String lastName;
}
