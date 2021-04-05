package by.radchuk.otus.account;

import java.math.BigDecimal;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "DTO represents the amount of money")
@AllArgsConstructor
@NoArgsConstructor
public class AmountDto {

  @Schema(description = "Amount of money")
  private BigDecimal amount;

}
