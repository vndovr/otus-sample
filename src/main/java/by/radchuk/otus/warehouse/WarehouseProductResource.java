package by.radchuk.otus.warehouse;

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
@Path("/warehouseproducts")
@Tag(name = "Warehouse Products API", description = "API for warehouse products manipulations")
@ApplicationScoped
public class WarehouseProductResource {

  @Inject
  WarehouseProductService warehousePorductService;

  @GET
  @Operation(summary = "Returns all products in warehouse.")
  @APIResponses(value = {
      @APIResponse(responseCode = "204", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = WarehouseProductDto[].class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "409", description = Descriptions.D409),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response getAll() {
    return Response.ok(warehousePorductService.getAll()).build();
  }

  @Path("/reserve/{xReqId}")
  @POST
  @Operation(summary = "(INTERNAL USE ONLY). Reserves goods for shipping.")
  @APIResponses(value = {@APIResponse(responseCode = "204", description = Descriptions.D200),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "409", description = Descriptions.D409),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response reserve(@PathParam("xReqId") String xReqId, ReservationDto[] dto) {
    warehousePorductService.reserve(xReqId, dto);
    return Response.noContent().build();
  }

  @Path("/release/{xReqId}")
  @POST
  @Operation(summary = "(INTERNAL USE ONLY). Release goods reserved for shipping.")
  @APIResponses(value = {@APIResponse(responseCode = "204", description = Descriptions.D200),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "409", description = Descriptions.D409),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response release(@PathParam("xReqId") String xReqId, ReservationDto[] dto) {
    warehousePorductService.release(xReqId, dto);
    return Response.noContent().build();
  }

}
