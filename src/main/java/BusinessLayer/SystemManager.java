package BusinessLayer;
//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
public class SystemManager {

    private String username;
    private String hashedPassword;
    @Column
    private String sessionId;

    public SystemManager(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.sessionId = null;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Column

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}