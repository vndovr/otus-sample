package by.radchuk.otus.system.jaxrs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO with reference information about dependent object")
public class RefDto {

  @Schema(description = "identifier for referenced object")
  private String id;

  /**
   * Builds the reference object from id
   * 
   * @param id
   * @return
   */
  public static RefDto valueOf(String id) {
    RefDto refDto = new RefDto();
    refDto.setId(id);
    return refDto;
  }
}
