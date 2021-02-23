package by.radchuk.otus.auth;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import by.radchuk.otus.system.jaxrs.ObjectNotFoundException;
import by.radchuk.otus.system.jaxrs.UnauthorizedException;
import io.quarkus.runtime.util.HashUtil;

@ApplicationScoped
@Transactional
public class UserService {

  Cache<String, String> cache =
      CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(5)).build();

  @Inject
  UserMapper userMapper;

  String getUser(String header) {
    return cache.getIfPresent(header);
  }

  void createUser(UserDto dto) {
    User user = userMapper.asUser(dto);
    user.setSalt(String.valueOf(RandomUtils.nextInt(10000, 99999)));
    user.setPassword(HashUtil.sha1("{" + user.getSalt() + "}" + user.getPassword()));
    User.persist(user);
  }

  void deleteUser(String id) {
    if (!User.deleteById(id)) {
      throw new ObjectNotFoundException();
    }
  }

  TokenDto login(UserDto dto) {
    User user = User.findById(dto.getLogin());
    if (Objects.isNull(user))
      throw new ObjectNotFoundException();
    if (StringUtils.equals(user.getPassword(),
        HashUtil.sha1("{" + user.getSalt() + "}" + dto.getPassword()))) {
      String token = UUID.randomUUID().toString();
      cache.put(token, user.getLogin());
      return new TokenDto(token);
    } else
      throw new UnauthorizedException();
  }

  void logout(String token) {
    cache.invalidate(token);
  }
}
