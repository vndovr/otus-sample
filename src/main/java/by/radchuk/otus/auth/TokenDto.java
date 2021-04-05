package by.radchuk.otus.auth;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO represtents the authorization token")
public class TokenDto {

  @Schema(description = "access token")
  private String token;
}
