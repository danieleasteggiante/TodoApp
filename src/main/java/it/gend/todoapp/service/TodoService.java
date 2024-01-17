package it.gend.todoapp.service;

import it.gend.todoapp.entity.Todo;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * @author Daniele Asteggiante
 */
@Stateless
public class TodoService {
    @Inject
    EntityManager em;

    public Todo getTodoByIdService(Integer id) {
        em.clear();
        return em.find(Todo.class, id);
    }
    public Todo createTodoService(Todo todo) {
        em.persist(todo);
        em.flush();
        return em.find(Todo.class, todo.getId());
    }
    public List<Todo> getChildren(Integer id) {
        em.clear();
        return em.find(Todo.class, id).getChildren();
    }
}
