package by.radchuk.otus.warehouse;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents warehouse products")
public class WarehouseProductDto {

  @Schema(description = "Product's identifier")
  String id;

  @Schema(description = "Product's name")
  String name;

  @Schema(description = "Available amount")
  long available;

  @Schema(description = "Reserved amount")
  long reserved;

}
