package by.radchuk.otus.system.jaxrs;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class ObjectNotFoundException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public ObjectNotFoundException() {
    super(Status.NOT_FOUND);
  }

}
