package by.radchuk.otus.orderevent;

import java.time.LocalDateTime;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents information delivery")
public class DeliveryDetailsDto {

  @Schema(description = "description for the order")
  private String description;

  @Schema(description = "delivery time for the order")
  private LocalDateTime deliveryTime;
}
