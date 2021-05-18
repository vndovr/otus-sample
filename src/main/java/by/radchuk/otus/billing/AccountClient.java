package by.radchuk.otus.billing;

import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "service.account-client")
public interface AccountClient {

  @Path("/{creditAccount}/{debitAccount}/{amount}/{xReqId}")
  @POST
  public Response transfer(@PathParam("creditAccount") String creditAccount,
      @PathParam("debitAccount") String debitAccount, @PathParam("amount") BigDecimal amount,
      @PathParam("xReqId") String xReqId);

  @Path("/{xReqId}")
  @DELETE
  public Response rollback(@PathParam("xReqId") String xReqId);
}
