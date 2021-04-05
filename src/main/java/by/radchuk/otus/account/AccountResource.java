package by.radchuk.otus.account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
import by.radchuk.otus.system.jaxrs.Descriptions;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/accounts")
@Tag(name = "Accounts API", description = "API for accounts manipulations")
@ApplicationScoped
public class AccountResource {

  @Inject
  AccountService accountService;

  @GET
  @Operation(summary = "Returns the amount of money on the account")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = AmountDto.class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response getAmount() {
    return Response.ok(accountService.getAmount()).build();
  }

  @PUT
  @Operation(summary = "Updates the amount of money (sum with what exists)")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = AmountDto.class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response updateAmount(AmountDto dto) {
    return Response.ok(accountService.updateAmount(dto)).build();
  }

}
