package BusinessLayer;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.*;
import java.util.Map;
import java.util.Map.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.atmosphere.annotation.AnnotationUtil.logger;

public class SessionManager {

    private Object sessionLock = new Object();
    private final Map<String, Guest> sessions;
    private final Map<String, SystemManager> systemManagerSessions;
    private static final int SESSION_ID_LENGTH = 16;

    public SessionManager() {
        this.systemManagerSessions = new ConcurrentHashMap<>();
        this.sessions = new ConcurrentHashMap<>();
    }

    public String createSession(Guest user) throws Exception {
        if (sessions.containsValue(user)) {
            logger.error(String.format("%s is already logged in", user.getUsername()));
            throw new Exception("this user is already logged in");
        }
        String sessionId;
        synchronized (this.sessionLock) {
            sessionId = generateSessionId();
            sessions.put(sessionId, user);
        }
        return sessionId;
    }

    public Guest getSession(String sessionId) throws Exception {
        System.out.println("here1");
        Guest g = null;
        try {
            g = sessions.get(sessionId);
        } catch (Exception e) {
            throw e;
        }
        System.out.println("here1.5");
        if (g == null) {
            System.out.println("here2");
            logger.error(String.format("the user isn't logged in"));
            throw new Exception("user session doesnt exist");
        }
        System.out.println("here3");
        return g;
    }

    public void deleteSession(String sessionId) throws Exception {
        Guest g = sessions.remove(sessionId);
        if (g == null) {
            logger.error(String.format("user session doesnt exist"));
            throw new Exception("user session doesnt exist");
        }
    }

    public String generateSessionId() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[SESSION_ID_LENGTH];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public String createSessionForSystemManager(SystemManager sm) throws Exception {
        if (systemManagerSessions.containsValue(sm)) {
            logger.error(String.format("%s is already logged in", sm.getUsername()));
            throw new Exception("this user is already logged in");
        }
        String sessionId;
        synchronized (this.sessionLock) {
            sessionId = generateSessionId();
            systemManagerSessions.put(sessionId, sm);
        }
        return sessionId;
    }

    public void deleteSessionForSystemManager(String sessionId) throws Exception {
        SystemManager sm = systemManagerSessions.remove(sessionId);
        if (sm == null) {
            logger.error(String.format("user session doesnt exist"));
            throw new Exception("user session doesnt exist");
        }
    }

    public SystemManager getSessionForSystemManager(String sessionId) throws Exception {
        SystemManager sm = systemManagerSessions.get(sessionId);
        if (sm == null) {
            logger.error(String.format("system manager session doesnt exist"));
            throw new Exception("system manager session doesnt exist");
        }
        return sm;
    }

    public String getSessionIdByGuestName(String name) throws Exception {
        String session = null;
        for (Entry<String, Guest> ent : sessions.entrySet()) {
            if (ent.getValue().getUsername().equals(name)) {
                session = ent.getKey();
            }
        }
        return session;
    }
}