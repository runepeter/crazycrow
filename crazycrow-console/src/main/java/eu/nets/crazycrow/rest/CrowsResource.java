package eu.nets.crazycrow.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/crows")
public class CrowsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok("[]").build();
    }
}
