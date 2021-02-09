package by.radchuk.otus.user;

import lombok.Data;

@Data
public class UserDtoWithId {
  Long id;

  String login;

  String firstName;

  String lastName;
}
