package by.radchuk.otus.account;

import java.math.BigDecimal;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import by.radchuk.otus.system.UserPrincipal;
import by.radchuk.otus.system.jaxrs.Descriptions;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/accounts")
@Tag(name = "Accounts API", description = "API for accounts manipulations")
@ApplicationScoped
public class AccountResource {

  @Inject
  UserPrincipal userPrincipal;

  @Inject
  AccountService accountService;

  @Path("/self")
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
  public Response getAmount(@QueryParam("userId") String userId) {
    if (!userPrincipal.hasRole("admin") || Objects.isNull(userId)) {
      userId = userPrincipal.getLogin();
    }
    return Response.ok(accountService.getAmount(userId)).build();
  }

  @Path("/self")
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
    return Response.ok(accountService.updateAmount(userPrincipal.getLogin(), dto)).build();
  }

  @Path("/{creditAccount}/{debitAccount}/{amount}/{xReqId}")
  @POST
  @Operation(summary = "(INTERNAL USE ONLY). Moves money from one user account to another.")
  @APIResponses(value = {
      @APIResponse(responseCode = "204", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = AmountDto.class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "409", description = Descriptions.D409),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response transfer(@PathParam("creditAccount") String creditAccount,
      @PathParam("debitAccount") String debitAccount, @PathParam("amount") BigDecimal amount,
      @PathParam("xReqId") String xReqId) {
    accountService.transfer(creditAccount, debitAccount, amount, xReqId);
    return Response.noContent().build();
  }

  @Path("/{xReqId}")
  @DELETE
  @Operation(summary = "(INTERNAL USE ONLY). Rolles back an existing transaction.")
  @APIResponses(value = {
      @APIResponse(responseCode = "204", description = Descriptions.D200,
          content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = AmountDto.class))),
      @APIResponse(responseCode = "400", description = Descriptions.D400),
      @APIResponse(responseCode = "401", description = Descriptions.D401),
      @APIResponse(responseCode = "403", description = Descriptions.D403),
      @APIResponse(responseCode = "404", description = Descriptions.D404),
      @APIResponse(responseCode = "409", description = Descriptions.D409),
      @APIResponse(responseCode = "500", description = Descriptions.D500)})
  public Response rollback(@PathParam("xReqId") String xReqId) {
    accountService.rollback(xReqId);
    return Response.noContent().build();
  }
}
