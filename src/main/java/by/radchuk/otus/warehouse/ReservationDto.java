package by.radchuk.otus.warehouse;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents reservation data")
public class ReservationDto {

  String itemId;

  long quantity;
}
