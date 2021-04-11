package by.radchuk.otus.notification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import by.radchuk.otus.system.UserPrincipal;
import by.radchuk.otus.system.jaxrs.Descriptions;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/notifications")
@Tag(name = "Notifications API", description = "API for notifications")
@ApplicationScoped
public class NotificationResource {

  @Inject
  NotificationService notificationService;

  @Inject
  UserPrincipal userPrincipal;

  @GET
  @Operation(summary = "Returns the notifications sent from system (for role 'admin' only)")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = NotificationDto[].class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response getNotifications() {
    return userPrincipal.hasRole("admin")
        ? Response.ok(notificationService.getNotifications()).build()
        : Response.noContent().build();
  }

}
