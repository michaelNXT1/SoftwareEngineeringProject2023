package org.example.BusinessLayer;

public class SystemManager {

    private String username;
    private String hashedPassword;

    public SystemManager(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getPassword() {
        return hashedPassword;
    }
}
