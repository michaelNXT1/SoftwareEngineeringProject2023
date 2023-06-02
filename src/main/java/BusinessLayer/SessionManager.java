package BusinessLayer;


import DAOs.MapStringGuestDAO;
import DAOs.MapStringSystemManagerDAO;
import Repositories.IMapStringGuestRepository;
import Repositories.IMapStringSystemManagerRepository;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.*;
import java.util.Map;
import java.util.Map.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.atmosphere.annotation.AnnotationUtil.logger;

public class SessionManager {

    private final Object sessionLock = new Object();
    private final IMapStringGuestRepository sessions;
    private final IMapStringSystemManagerRepository systemManagerSessions;
    private static final int SESSION_ID_LENGTH = 16;
    public SessionManager() {
        this.systemManagerSessions = new MapStringSystemManagerDAO();
        this.sessions = new MapStringGuestDAO(new ConcurrentHashMap<>());
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
        Guest g;
        g = sessions.get(sessionId);
        if (g == null) {
            logger.error("the user isn't logged in");
            throw new Exception("user session doesnt exist");
        }
        return g;
    }

    public void deleteSession(String sessionId) throws Exception {
        Guest g = sessions.get(sessionId);
        if (g == null) {
            logger.error("user session doesnt exist");
            throw new Exception("user session doesnt exist");
        }
        sessions.remove(sessionId);
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
            systemManagerSessions.addSystemManager(sessionId, sm);
        }
        return sessionId;
    }

    public void deleteSessionForSystemManager(String sessionId) throws Exception {
        SystemManager sm = systemManagerSessions.getSystemManager(sessionId);
        if (sm == null) {
            logger.error("user session doesnt exist");
            throw new Exception("user session doesnt exist");
        }
        systemManagerSessions.removeSystemManager(sessionId);
    }

    public SystemManager getSessionForSystemManager(String sessionId) throws Exception {
        SystemManager sm = systemManagerSessions.getSystemManager(sessionId);
        if (sm == null) {
            logger.error("system manager session doesnt exist");
            throw new Exception("system manager session doesnt exist");
        }
        return sm;
    }

    public String getSessionIdByGuestName(String name) throws Exception {
        String session = null;
        for (Entry<String, Guest> ent : sessions.getAllGuests().entrySet()) {
            if (ent.getValue().getUsername().equals(name)) {
                session = ent.getKey();
            }
        }
        return session;
    }
}