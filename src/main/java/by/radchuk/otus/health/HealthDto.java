package by.radchuk.otus.health;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO represents health status")
public class HealthDto {

  @Schema(description = "status of the system")
  private String status;
}
