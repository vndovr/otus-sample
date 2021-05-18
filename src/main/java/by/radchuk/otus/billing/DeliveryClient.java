package by.radchuk.otus.billing;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "service.delivery-client")
public interface DeliveryClient {

  @Path("/{date}/reserve/{xReqId}")
  @POST
  public void reserve(@PathParam("date") String date, @PathParam("xReqId") String xReqId);

  @Path("/{date}/release/{xReqId}")
  @POST
  public void release(@PathParam("date") String date, @PathParam("xReqId") String xReqId);

}
