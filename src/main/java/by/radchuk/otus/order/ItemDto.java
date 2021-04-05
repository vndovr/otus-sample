package by.radchuk.otus.order;

import java.math.BigDecimal;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents single order item")
public class ItemDto {

  @Schema(description = "item's identifier")
  String itemId;

  @Schema(description = "item's name")
  String itemName;

  @Schema(description = "item's quantity")
  int quantity;

  @Schema(description = "item's price")
  BigDecimal price;
}
