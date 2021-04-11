package by.radchuk.otus.notification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.smallrye.reactive.messaging.annotations.Blocking;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@Transactional
public class NotificationListener {

  @Inject
  Jsonb jsonb;

  @Inject
  MailSender mailSender;

  @Inject
  NotificationService notificationService;

  @Inject
  NotificationMapper notificationMapper;

  @Inject
  @RestClient
  ProfileClient profileClient;

  @Incoming("notification-events-in")
  @Blocking
  public void onMessage(String event) {
    log.info("Got an event to send the notification");
    NotificationDto dto =
        notificationMapper.asNotificationDto(jsonb.fromJson(event, NotificationEventDto.class));

    dto.setEmail(profileClient.getProfile(dto.getUserId()).getEmail());

    notificationService.createNotification(dto);
  }
}
