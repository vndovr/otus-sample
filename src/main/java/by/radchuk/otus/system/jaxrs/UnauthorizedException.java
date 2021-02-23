package by.radchuk.otus.system.jaxrs;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  @Override
  public Response getResponse() {
    return Response.status(Status.UNAUTHORIZED).build();
  }

}
