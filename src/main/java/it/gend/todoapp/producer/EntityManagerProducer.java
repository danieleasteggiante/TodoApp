package it.gend.todoapp.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * @author Daniele Asteggiante
 */
@ApplicationScoped
public class EntityManagerProducer {
    @Produces
    public EntityManager produceEntityManager() {
        return Persistence
                .createEntityManagerFactory("todo_persistence")
                .createEntityManager();
    }
}
