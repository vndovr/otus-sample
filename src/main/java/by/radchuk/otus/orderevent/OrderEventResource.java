package by.radchuk.otus.orderevent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import by.radchuk.otus.order.Order;
import by.radchuk.otus.system.jaxrs.Descriptions;
import by.radchuk.otus.system.jaxrs.RefDto;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/events/orders")
@Tag(name = "Events API", description = "API for order events")
@ApplicationScoped
public class OrderEventResource {

  static String ORDER_EVENT_ENTITY = Order.class.getName();

  @Inject
  EventService orderEventService;

  @Inject
  EventMapper eventMapper;

  @POST
  @Operation(summary = "Registers new order in the system")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = RefDto.class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response registerOrder() {
    return Response.ok(
        orderEventService.createEvent(null, ORDER_EVENT_ENTITY, OrderEvents.createEvent(), null))
        .build();
  }

  @Path("/{id}/{itemId}/{quantity}")
  @POST
  @Operation(
      summary = "Amends the order in the system, change the quantity (could be negative) for items for the specific itemId")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = RefDto.class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response amendOrder(@PathParam("id") String id, @PathParam("itemId") String itemId,
      @PathParam("quantity") int quantity, @QueryParam("xReqId") String xReqId) {
    return Response.ok(orderEventService.createEvent(id, ORDER_EVENT_ENTITY,
        OrderEvents.amendEvent(itemId, quantity), xReqId)).build();
  }

  @Path("/{id}")
  @GET
  @Operation(summary = "Returns all events for the specific order")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = OrderEventDto[].class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response getOrderEvents(@PathParam("id") String id) {
    return Response.ok(orderEventService.getEvents(id, eventMapper::asOrderEventDto)).build();
  }

  @Path("/{id}")
  @DELETE
  @Operation(summary = "Cancels the order")
  @APIResponses(value = {@APIResponse(responseCode = "200", description = Descriptions.D200),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response cancelOrder(@PathParam("id") String id) {
    orderEventService.createEvent(id, ORDER_EVENT_ENTITY, OrderEvents.cancelEvent(), null);
    return Response.ok().build();
  }

  @Path("/{id}")
  @POST
  @Operation(summary = "Submits the order for billing")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = RefDto.class))),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response submitOrder(@PathParam("id") String id, DescriptionDto dto) {
    return Response.ok(orderEventService.createEvent(id, ORDER_EVENT_ENTITY,
        OrderEvents.sendToBilling(dto.getDescription()), null)).build();
  }
}

