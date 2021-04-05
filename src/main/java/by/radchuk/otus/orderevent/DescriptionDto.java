package by.radchuk.otus.orderevent;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents information about order description")
public class DescriptionDto {

  @Schema(description = "description for the order")
  private String description;
}
