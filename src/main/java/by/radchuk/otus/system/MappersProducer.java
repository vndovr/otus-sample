package by.radchuk.otus.system;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.mapstruct.factory.Mappers;
import by.radchuk.otus.auth.UserMapper;
import by.radchuk.otus.person.PersonMapper;
import by.radchuk.otus.profile.ProfileMapper;

@ApplicationScoped
public class MappersProducer {

  @Produces
  @ApplicationScoped
  public ProfileMapper profileMapper() {
    return Mappers.getMapper(ProfileMapper.class);
  }

  @Produces
  @ApplicationScoped
  public UserMapper userMapper() {
    return Mappers.getMapper(UserMapper.class);
  }

  @Produces
  @ApplicationScoped
  public PersonMapper personMapper() {
    return Mappers.getMapper(PersonMapper.class);
  }
}
