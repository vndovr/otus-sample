package by.radchuk.otus.system.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

/**
 * Indicates that update conflict occured
 * 
 * @author Vladimir Radchuk
 *
 */
public class UpdateConflictException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public UpdateConflictException() {
    super(Status.CONFLICT);
  }
}
