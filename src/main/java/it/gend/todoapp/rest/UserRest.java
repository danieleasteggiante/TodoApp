package it.gend.todoapp.rest;

import it.gend.todoapp.entity.User;
import it.gend.todoapp.service.TodoService;
import it.gend.todoapp.service.UserService;
import jakarta.ejb.EJB;
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
@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserRest {
    @EJB
    UserService userService;
    @Inject
    Logger logger;

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") Integer id) {
        logger.info("Get User by id: " + id );
        return Response.ok(userService.getUserByIdService(id)).build();
    }
    @POST
    public Response createUser(User user) {
        logger.info("Create User: " + user );
        return Response.ok(userService.createUserService(user)).build();
    }
}