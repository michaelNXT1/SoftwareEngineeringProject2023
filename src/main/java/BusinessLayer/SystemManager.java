package BusinessLayer;
//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
@Table(name = "system_managers")
public class SystemManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", columnDefinition = "text")
    private String username;
    @Column(name = "hash_password", columnDefinition = "text")
    private String hashedPassword;

    public SystemManager(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.id = 0L; // Initializing with a default value
    }

    public String getPassword() {
        return hashedPassword;
    }

    public String getUsername() {
        return username;
    }
}
