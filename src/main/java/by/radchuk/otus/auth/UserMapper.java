package by.radchuk.otus.auth;

import javax.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;

/**
 * Mapper for entity to dto mappings
 * 
 * @author Vladimir Radchuk
 *
 */
@Mapper
@ApplicationScoped
public interface UserMapper {

  UserDto asUserDto(User user);

  User asUser(UserDto userDto);
}
