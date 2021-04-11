package by.radchuk.otus.system;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.mapstruct.factory.Mappers;
import by.radchuk.otus.auth.UserMapper;
import by.radchuk.otus.notification.NotificationMapper;
import by.radchuk.otus.order.OrderMapper;
import by.radchuk.otus.orderevent.EventMapper;
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
  public EventMapper eventMapper() {
    return Mappers.getMapper(EventMapper.class);
  }

  @Produces
  @ApplicationScoped
  public OrderMapper orderMapper() {
    return Mappers.getMapper(OrderMapper.class);
  }

  @Produces
  @ApplicationScoped
  public NotificationMapper notificationMapper() {
    return Mappers.getMapper(NotificationMapper.class);
  }
}
