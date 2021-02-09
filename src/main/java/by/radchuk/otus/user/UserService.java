package by.radchuk.otus.user;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserService {

  @Inject
  UserMapper userMapper;

  List<UserDtoWithId> getUsers() {
    User user = new User();
    user.id = 1L;
    user.setFirstName("Vladimir");
    user.setLastName("Radchuk");
    user.setLogin("vndovr");
    List<User> users = Collections.singletonList(user); // = User.listAll();
    return users.stream().map(userMapper::asUserDtoWithId).collect(Collectors.toList());
  }
}
