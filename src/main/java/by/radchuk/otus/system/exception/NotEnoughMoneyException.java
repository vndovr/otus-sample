package by.radchuk.otus.system.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class NotEnoughMoneyException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public NotEnoughMoneyException() {
    super(Status.CONFLICT);
  }

}
