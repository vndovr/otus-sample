package by.radchuk.otus.order;

import java.time.LocalDateTime;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents normalized order")
public class OrderOverviewDto {

  @Schema(description = "order's identifier")
  private String id;

  @Schema(description = "user who created an order")
  private String userId;

  @Schema(description = "order's creation date")
  private LocalDateTime creationDate;

  @Schema(description = "order's description")
  private String description;

  @Schema(description = "order's current state")
  private String state;

  @Schema(description = "order's items")
  private List<ItemDto> items;
}
