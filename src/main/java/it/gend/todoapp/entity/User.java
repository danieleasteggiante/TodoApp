package it.gend.todoapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Daniele Asteggiante
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    Integer id;
    @Column(name = "username")
    String username;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
