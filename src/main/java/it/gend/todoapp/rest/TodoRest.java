package it.gend.todoapp.rest;

/**
 * @author Daniele Asteggiante
 */
import it.gend.todoapp.entity.Todo;
import it.gend.todoapp.entity.User;
import it.gend.todoapp.service.TodoService;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.util.logging.Logger;

@Path("/todo")
@Produces("application/json")
@Consumes("application/json")
public class TodoRest {
    @Inject
    Logger logger;
    @EJB
    TodoService todoService;

    @GET
    @Path("{id}")
    public Response getTodoById(@PathParam("id") Integer id) {
        logger.info("Get todo by Id...");
        return Response.ok(todoService.getTodoByIdService(id)).build();
    }
    @GET
    @Path("/children/{id}")
    public Response getTodoChildrenById(@PathParam("id") Integer id) {
        logger.info("Get todo children by Id...");
        return Response.ok(todoService.getChildren(id)).build();
    }
    @POST
    public Response createTodo(Todo todo) {
        logger.info("Create new todo...");
        return Response.ok(todoService.createTodoService(todo)).build();
    }
}