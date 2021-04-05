package by.radchuk.otus.orderevent;

import java.time.LocalDateTime;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents information about order event")
public class OrderEventDto {

  @Schema(description = "order's identifier")
  String externalId;

  @Schema(description = "type of the event")
  String type;

  @Schema(description = "event's data")
  String data;

  @Schema(description = "user who created the event")
  String userId;

  @Schema(description = "event's timestamp")
  LocalDateTime creationDate;
}
