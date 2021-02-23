package by.radchuk.otus.profile;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import by.radchuk.otus.health.HealthDto;
import by.radchuk.otus.system.UserPrincipal;
import by.radchuk.otus.system.jaxrs.Descriptions;
import lombok.extern.slf4j.Slf4j;

@Path("/profiles")
@Tag(name = "Profiles API", description = "API for profile manipulations")
@Slf4j
public class UserResource {

  @Inject
  ProfileService profileService;

  @Inject
  UserPrincipal userPrincipal;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Returns the profile for current user")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = HealthDto.class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  @Path("/self")
  public Response getProfile() {
    log.info("Getting profile for: {}", userPrincipal.getLogin());
    return userPrincipal.anonymouse() ? Response.noContent().build()
        : Response.ok(profileService.getProfile(userPrincipal.getLogin())).build();
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/self")
  @Operation(summary = "Updates the profile for current user")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = ProfileDto.class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response updateProfile(ProfileDto dto) {
    log.info("Update profile for: {}", userPrincipal.getLogin());
    log.info("DTO to update is: {}", dto);
    if (userPrincipal.anonymouse()
        || !StringUtils.equals(dto.getLogin(), userPrincipal.getLogin())) {
      throw new BadRequestException();
    }
    return Response.ok(profileService.updateProfile(dto)).build();
  }

}
