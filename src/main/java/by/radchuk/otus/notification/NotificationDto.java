package by.radchuk.otus.notification;

import java.time.LocalDateTime;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents the notification to the user")
public class NotificationDto {

  @Schema(description = "user's id")
  String userId;

  @Schema(description = "user's email")
  String email;

  @Schema(description = "notification subject")
  String subject;

  @Schema(description = "notification body")
  String body;

  @Schema(description = "time notification was created")
  LocalDateTime createdAt;
}
