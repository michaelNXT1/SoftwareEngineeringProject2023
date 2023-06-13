package Notification;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Entity
public class Notification {
    @Column
    private String message;
    @Column
    private LocalDateTime createdAt;
    @Id
    @GeneratedValue
    private long id;


    public Notification(String message) {
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public Notification() {

    }


    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }
}
