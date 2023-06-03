package BusinessLayer;
//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
public class SystemManager {
    @Id
    @Column(name = "username", columnDefinition = "text")
    private String username;
    @Column(name = "hashedPassword", columnDefinition = "text")
    private String hashedPassword;

    public SystemManager(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public SystemManager() {
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}