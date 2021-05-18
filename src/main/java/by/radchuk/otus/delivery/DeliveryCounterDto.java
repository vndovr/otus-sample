package by.radchuk.otus.delivery;

import java.time.LocalDate;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents delivery counters for days")
public class DeliveryCounterDto {

  @Schema(description = "Date of the delivery")
  LocalDate id;

  @Schema(description = "Number of deliveries")
  int counter;
}
