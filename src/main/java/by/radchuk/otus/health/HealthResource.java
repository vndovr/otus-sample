package by.radchuk.otus.health;

import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/health")
public class HealthResource {

  @GET
  public Response health() {
    return Response.ok(Json.createObjectBuilder().add("status", "OK").build().toString()).build();
  }
}
