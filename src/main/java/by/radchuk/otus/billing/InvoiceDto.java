package by.radchuk.otus.billing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "DTO represents the invoice")
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {

  @Schema(description = "user's id")
  String userId;

  @Schema(description = "order invoice issued for")
  String orderId;

  @Schema(description = "amount of money requested")
  BigDecimal amount;

  @Schema(description = "order description")
  String description;

  @Schema(description = "order's delivery time")
  LocalDateTime deliveryTime;

  ReservationDto[] items;
}
