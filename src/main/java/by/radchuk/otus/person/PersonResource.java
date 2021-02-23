package by.radchuk.otus.person;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/persons")
public class PersonResource {

  @Inject
  PersonService personService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getPersons() {
    return Response.ok(personService.getPersons()).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  public Response getPerson(@PathParam("id") long id) {
    return Response.ok(personService.getPerson(id)).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createPerson(PersonDto dto) {
    return Response.status(Status.CREATED).entity(personService.createPerson(dto)).build();
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  public Response updatePerson(@PathParam("id") long id, PersonDtoWithId dto) {
    dto.setId(id);
    return Response.ok(personService.updatePerson(dto)).build();
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  public Response deletePerson(@PathParam("id") long id) {
    personService.deletePerson(id);
    return Response.noContent().build();
  }
}
