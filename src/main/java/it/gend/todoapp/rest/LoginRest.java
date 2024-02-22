package it.gend.todoapp.rest;

import it.gend.todoapp.DTO.LoginDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;
/**
 * @author Daniele Asteggiante
 */
@Path("/login")
@Produces("application/json")
@Consumes("application/json")
public class LoginRest {
    @Inject
    Logger logger;
    @POST
    public Response getUserById(LoginDTO loginDTO) {
        logger.info("tentativo di login: " + loginDTO.getUsername() + " " + loginDTO.getPassword());
        if(loginDTO.getUsername().equals("admin") && loginDTO.getPassword().equals("admin"))
            return Response.ok().build();
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
