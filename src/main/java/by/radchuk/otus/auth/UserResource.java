package by.radchuk.otus.auth;

import java.util.Objects;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.spi.HttpRequest;
import by.radchuk.otus.system.jaxrs.Descriptions;
import by.radchuk.otus.system.jaxrs.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/auth")
@Slf4j
@Tag(name = "Authentication API", description = "API for users authorization")
public class UserResource {

  @Inject
  UserService userService;

  @GET
  @Operation(summary = "Checks if user is already authenticated")
  @APIResponses(value = {@APIResponse(responseCode = "200", description = Descriptions.D200),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response validate(@Context HttpRequest request) {
    log.info("Got request to validate authentication");
    Cookie cookie = request.getHttpHeaders().getCookies().get("session");
    log.info("Cookie: {}", cookie);
    if (Objects.isNull(cookie) || StringUtils.isBlank(cookie.getValue())) {
      throw new UnauthorizedException();
    }
    String user = userService.getUser(cookie.getValue());
    String roles = userService.getRoles(cookie.getValue());

    log.info("Returned user: {}", user);
    if (Objects.isNull(user) || Objects.isNull(roles)) {
      throw new UnauthorizedException();
    }
    return Response.ok().header("X-User", user).header("X-Roles", roles).build();
  }

  @POST
  @Operation(summary = "Registers a new user")
  @APIResponses(value = {@APIResponse(responseCode = "201", description = Descriptions.D200),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  @Path("/register")
  public Response registerUser(UserDto dto) {
    userService.createUser(dto);
    return Response.status(Status.CREATED).build();
  }

  @DELETE
  @Path("/{id}")
  @Operation(summary = "Deletes the user identified by ID")
  @APIResponses(value = {@APIResponse(responseCode = "204", description = Descriptions.D200),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response deleteUser(@PathParam("id") String id) {
    userService.deleteUser(id);
    return Response.noContent().build();
  }

  @POST
  @Operation(summary = "Login a user with login and password")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = TokenDto.class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  @Path("/login")
  public Response loginUsers(UserDto dto) {
    TokenDto result = userService.login(dto);
    return Response.ok(result).build();
  }

  @POST
  @Operation(summary = "Logout user's session")
  @APIResponses(value = {@APIResponse(responseCode = "204", description = Descriptions.D200),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  @Path("/logout")
  public Response logout(TokenDto dto) {
    userService.logout(dto.getToken());
    return Response.noContent().build();
  }

}
