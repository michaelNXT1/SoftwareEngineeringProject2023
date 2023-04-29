package BusinessLayer;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private Object sessionLock = new Object();
    private final Map<String, Guest> sessions;
    private final Map<String, SystemManager> systemManagerSessions;
    private static final int SESSION_ID_LENGTH = 16;

    public SessionManager() {
        this.systemManagerSessions = new ConcurrentHashMap<>();
        this.sessions = new ConcurrentHashMap<>();
    }

    public String createSession(Guest user) {
        String sessionId;
        synchronized (this.sessionLock) {
            sessionId = generateSessionId();
            sessions.put(sessionId, user);
        }
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

    public String createSessionForSystemManager(SystemManager sm) {
        String sessionId;
        synchronized (this.sessionLock) {
            sessionId = generateSessionId();
            systemManagerSessions.put(sessionId, sm);
        }
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