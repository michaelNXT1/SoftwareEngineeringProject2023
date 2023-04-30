package BusinessLayer;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private final Map<String, Guest> sessions;
    private final Map<String, SystemManager> systemManagerSessions;
    private static final int SESSION_ID_LENGTH = 16;

    public SessionManager() {
        this.systemManagerSessions = new HashMap<>();
        this.sessions = new HashMap<>();
    }

    public String createSession(Guest user) throws Exception {
        if (sessions.containsValue(user))
            throw new Exception("this user is already logged in");
        String sessionId = generateSessionId();
        sessions.put(sessionId, user);
        return sessionId;
    }
    public Guest getSession(String sessionId) throws Exception {
        Guest g = sessions.get(sessionId);
        if (g == null)
            throw new Exception("user session doesnt exist");
        return g;
    }

    public void deleteSession(String sessionId) throws Exception {
        Guest g = sessions.remove(sessionId);
        if (g == null)
            throw new Exception("user session doesnt exist");
    }
    public String generateSessionId() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[SESSION_ID_LENGTH];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public String createSessionForSystemManager(SystemManager sm) throws Exception {
        if (systemManagerSessions.containsValue(sm))
            throw new Exception("this user is already logged in");
        String sessionId = generateSessionId();
        systemManagerSessions.put(sessionId, sm);
        return sessionId;
    }
    public void deleteSessionForSystemManager(String sessionId) throws Exception {
        SystemManager sm = systemManagerSessions.remove(sessionId);
        if (sm == null)
            throw new Exception("user session doesnt exist");
    }

    public SystemManager getSessionForSystemManager(String sessionId) throws Exception {
        SystemManager sm = systemManagerSessions.get(sessionId);
        if (sm == null)
            throw new Exception("system manager session doesnt exist");
        return sm;
    }
}