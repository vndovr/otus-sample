package by.radchuk.otus.notification;

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
public interface NotificationMapper {

  NotificationDto asNotificationDto(NotificationEventDto dto);

  NotificationDto asNotificationDto(Notification notification);

  Notification asNotification(NotificationDto dto);
}
