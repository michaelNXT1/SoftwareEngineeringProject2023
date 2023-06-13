package BusinessLayer;
//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
public class SystemManager {

    private String username;
    private String hashedPassword;

    public SystemManager(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public SystemManager() {
    }
    @Id
    @Column
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Column

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}