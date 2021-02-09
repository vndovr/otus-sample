package by.radchuk.otus.user;

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

  UserDtoWithId asUserDtoWithId(User user);
}
