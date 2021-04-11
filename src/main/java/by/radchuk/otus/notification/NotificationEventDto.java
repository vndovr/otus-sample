package by.radchuk.otus.notification;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO represents the notification to the user")
public class NotificationEventDto {

  @Schema(description = "user's id")
  String userId;

  @Schema(description = "notification subject")
  String subject;

  @Schema(description = "notification body")
  String body;
}
