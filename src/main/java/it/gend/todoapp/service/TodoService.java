package it.gend.todoapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gend.todoapp.entity.Todo;
import it.gend.todoapp.entity.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Daniele Asteggiante
 */
@Stateless
public class TodoService {
    @Inject
    EntityManager em;
    @Inject
    Logger logger;
    public Todo getTodoByIdService(Integer id) {
        em.clear();
        return em.find(Todo.class, id);
    }
    public List<Todo> createTodoService(String todo) {
        Todo rootTodo = null;
        try {
            rootTodo = new ObjectMapper().readValue(todo, Todo.class);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
        }

       if(rootTodo == null)
           throw new RuntimeException("Invalid Root Todo");

        Set<Todo> todoSet = getListFromTree(rootTodo);
        todoSet.add(rootTodo);
        List<Todo> ordered = new ArrayList<>(todoSet);
        ordered = ordered.stream()
                .peek(t -> t.setChildren(null))
                .sorted(Comparator.comparing(Todo::getId))
                .peek(t -> em.merge(t))
                .collect(Collectors.toList());
        em.flush();
        em.clear();
        return ordered;
    }

    private Set<Todo> getListFromTree(Todo rootTodo) {
        Set<Todo> todoSet = new HashSet<>();
        decodeTree(rootTodo, todoSet);
        return todoSet;
    }

    private void decodeTree(Todo rootTodo, Set<Todo> todoSet) {
        todoSet.add(rootTodo);
        if(rootTodo.getChildren().isEmpty()){
            rootTodo.setChildren(null);
            return;
        }
        rootTodo.getChildren().forEach(todo -> {
            decodeTree(todo, todoSet);
        });
    }

    public List<Todo> getChildren(Integer id) {
        em.clear();
        return em.find(Todo.class, id).getChildren();
    }

    public Boolean deleteTodoService(Integer id) {
        Todo todo = em.find(Todo.class, id);
        if(todo.getParentId() != null)
            todo.setParentId(null);
        em.merge(todo);
        em.flush();
        em.clear();
        todo = em.find(Todo.class, id);
        try {
            em.remove(todo);
            return true;
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    public List<Todo> getTodoByUser(Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Todo> cr = cb.createQuery(Todo.class);
        Root<Todo> root = cr.from(Todo.class);
        Predicate predicate = cb.equal(root.get("userId"), id);
        cr.select(root).where(predicate);
        return em.createQuery(cr).getResultList();
    }
}
