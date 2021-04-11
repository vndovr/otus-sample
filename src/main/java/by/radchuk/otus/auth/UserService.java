package by.radchuk.otus.auth;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.transaction.Transactional;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import by.radchuk.otus.system.exception.ObjectNotFoundException;
import by.radchuk.otus.system.exception.UnauthorizedException;
import io.quarkus.runtime.util.HashUtil;

@ApplicationScoped
@Transactional
public class UserService {

  Cache<String, String> users =
      CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(5)).build();

  Cache<String, String> roles =
      CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(5)).build();

  @Inject
  UserMapper userMapper;

  @Inject
  @Channel("user-events-out")
  Emitter<String> emitter;

  String getUser(String header) {
    return users.getIfPresent(header);
  }

  String getRoles(String header) {
    return roles.getIfPresent(header);
  }

  void createUser(UserDto dto) {
    User user = userMapper.asUser(dto);
    user.setSalt(String.valueOf(RandomUtils.nextInt(10000, 99999)));
    user.setPassword(HashUtil.sha1("{" + user.getSalt() + "}" + user.getPassword()));
    user.setRoles("user");
    User.persist(user);

    emitter.send(Json.createObjectBuilder().add("userId", dto.getLogin()).add("operation", "create")
        .build().toString());
  }

  void deleteUser(String id) {
    if (!User.deleteById(id)) {
      throw new ObjectNotFoundException();
    } else {
      emitter.send(Json.createObjectBuilder().add("userId", id).add("operation", "delete").build()
          .toString());
    }
  }

  TokenDto login(UserDto dto) {
    User user = User.findById(dto.getLogin());
    if (Objects.isNull(user))
      throw new ObjectNotFoundException();
    if (StringUtils.equals(user.getPassword(),
        HashUtil.sha1("{" + user.getSalt() + "}" + dto.getPassword()))) {
      String token = UUID.randomUUID().toString();
      users.put(token, user.getLogin());
      roles.put(token, user.getRoles());
      return new TokenDto(token);
    } else
      throw new UnauthorizedException();
  }

  void logout(String token) {
    users.invalidate(token);
    roles.invalidate(token);
  }
}
