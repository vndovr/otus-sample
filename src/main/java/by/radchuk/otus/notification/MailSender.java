package by.radchuk.otus.notification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
class MailSender {

  @Inject
  NotificationService notificationService;

  /**
   * Creates the notification
   * 
   * @param userId
   * @param to
   * @param subject
   * @param body
   */
  void send(String to, String subject, String body) {
    log.info("Send the notification to [{}] with subject: {}", to, subject);
  }
}
