package by.radchuk.otus.delivery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import by.radchuk.otus.system.jaxrs.Descriptions;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/deliveries")
@Tag(name = "Delivery API", description = "API for managing deliveries")
@ApplicationScoped
public class DeliveryResource {

  @Inject
  DeliveryService deliveryService;

  @GET
  @Operation(summary = "Returns all delivery counters.")
  @APIResponses(value = {
      @APIResponse(responseCode = "204", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = DeliveryCounterDto[].class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "409", description = Descriptions.D409),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response getAll() {
    return Response.ok(deliveryService.getAll()).build();
  }

  @Path("/{date}/reserve/{xReqId}")
  @POST
  @Operation(summary = "(INTERNAL USE ONLY). Reserves a time for delivery")
  @APIResponses(value = {@APIResponse(responseCode = "204", description = Descriptions.D200),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "409", description = Descriptions.D409),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response reserve(@PathParam("date") String date, @PathParam("xReqId") String xReqId) {
    deliveryService.reserve(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyyMMddHHmm")),
        xReqId);
    return Response.noContent().build();
  }

  @Path("/{date}/release/{xReqId}")
  @POST
  @Operation(summary = "(INTERNAL USE ONLY). Releases goods after shipping.")
  @APIResponses(value = {@APIResponse(responseCode = "204", description = Descriptions.D200),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "409", description = Descriptions.D409),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response release(@PathParam("date") String date, @PathParam("xReqId") String xReqId) {
    deliveryService.release(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyyMMddHHmm")),
        xReqId);
    return Response.noContent().build();
  }

}
