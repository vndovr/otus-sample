package by.radchuk.otus.notification;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
@Transactional
public class NotificationService {

  @Inject
  NotificationMapper notificationMapper;

  /**
   * Registerns new notification in the database
   * 
   * @param notification
   */
  public void createNotification(NotificationDto notification) {
    Notification.persist(notificationMapper.asNotification(notification));
  }

  /**
   * Returns all notifications sent from system
   * 
   * @return
   */
  public List<NotificationDto> getNotifications() {
    List<Notification> result = Notification.listAll(Sort.ascending("id"));
    return result.stream().map(notificationMapper::asNotificationDto).collect(Collectors.toList());
  }

}
