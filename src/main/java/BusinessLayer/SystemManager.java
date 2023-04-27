package BusinessLayer;

public class SystemManager {

    private String username;
    private String email;
    private String hashedPassword;

    public SystemManager(String username, String email, String hashedPassword) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public String getPassword() {
        return hashedPassword;
    }
}
