package org.example.BusinessLayer;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private final Map<String, Guest> sessions;
    private static final int SESSION_ID_LENGTH = 16;

    public SessionManager() {
        this.sessions = new HashMap<>();
    }

    public String createSession(Guest user) {
        String sessionId = generateSessionId();
        sessions.put(sessionId, user);
        return sessionId;
    }
    public Guest getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        sessions.remove(sessionId);
    }
    public String generateSessionId() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[SESSION_ID_LENGTH];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
