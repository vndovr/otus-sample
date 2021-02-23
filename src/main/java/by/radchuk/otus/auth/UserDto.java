package by.radchuk.otus.auth;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents the user information")
public class UserDto {

  @Schema(description = "User's login")
  String login;

  @Schema(description = "User's password")
  String password;
}
