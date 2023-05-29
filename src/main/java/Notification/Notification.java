package Notification;


import java.time.LocalDateTime;



public class Notification {
    private String message;
    private LocalDateTime createdAt;
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
