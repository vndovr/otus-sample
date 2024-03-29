package by.radchuk.otus.profile;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO represents the user with information about ID and version")
public class ProfileDto {

  @Schema(description = "User's id")
  String userId;

  @Schema(description = "User's first name")
  String firstName;

  @Schema(description = "User's last name")
  String lastName;

  @Schema(description = "User's email")
  String email;

  @Schema(description = "User's city")
  String city;

  @Schema(description = "User's postal code")
  String postalCode;

  @Schema(description = "User's address")
  String addressLine;

  @Schema(description = "User's phone")
  String phone;

  @Schema(description = "Version for optimistic locking")
  long version;
}
