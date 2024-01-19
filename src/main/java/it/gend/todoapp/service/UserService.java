package it.gend.todoapp.service;

import it.gend.todoapp.entity.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
/**
 * @author Daniele Asteggiante
 */
@Stateless
public class UserService {
    @Inject
    EntityManager em;
    public User getUserByIdService(Integer id) {
        return em.find(User.class, id);
    }
    public User createUserService(User user) {
        em.persist(user);
        return em.find(User.class, user.getId());
    }
}
