package by.radchuk.otus.system;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.mapstruct.factory.Mappers;

import by.radchuk.otus.user.UserMapper;

@ApplicationScoped
public class MappersProducer {

  @Produces
  @ApplicationScoped
  public UserMapper userMapper() {
    return Mappers.getMapper(UserMapper.class);
  }
}
