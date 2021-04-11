package by.radchuk.otus.notification;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import by.radchuk.otus.profile.ProfileDto;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "service.profile-client")
public interface ProfileClient {

  @GET
  public ProfileDto getProfile(@QueryParam("userId") String userId);
}
