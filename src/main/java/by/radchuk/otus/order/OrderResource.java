package by.radchuk.otus.order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import by.radchuk.otus.system.UserPrincipal;
import by.radchuk.otus.system.jaxrs.Descriptions;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import lombok.extern.slf4j.Slf4j;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/orders")
@Tag(name = "Orders API", description = "API for orders")
@ApplicationScoped
@Slf4j
public class OrderResource {

  @Inject
  OrderService orderService;

  @Inject
  UserPrincipal userPrincipal;

  @GET
  @Operation(summary = "Returns all orders with search criterias")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = OrderDto[].class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response getOrders(@QueryParam("state") String state, @QueryParam("sort") String sort) {
    log.info("getOrders()");
    log.info("State: {}", state);
    log.info("Sort:  {}", sort);
    return Response
        .ok(orderService
            .getOrders(userPrincipal.hasRole("admin") ? null : userPrincipal.getLogin(),
                StringUtils.isBlank(state) ? null : Order.State.valueOf(state),
                StringUtils.isBlank(sort) ? Sort.by("id")
                    : Sort.by(sort.charAt(0) == '-' ? sort.substring(1) : sort,
                        sort.charAt(0) == '-' ? Direction.Descending : Direction.Ascending)))
        .build();
  }

  @Path("/{id}")
  @GET
  @Operation(summary = "Returns an order identified by id")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = OrderDto[].class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response getOrder(@PathParam("id") String id) {
    return Response.ok(
        orderService.getOrder(id, userPrincipal.hasRole("admin") ? null : userPrincipal.getLogin()))
        .build();
  }



}
