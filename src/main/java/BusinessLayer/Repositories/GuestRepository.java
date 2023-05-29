package BusinessLayer.Repositories;

import BusinessLayer.Guest;
import BusinessLayer.Repositories.Interfaces.IGuestRepository;

import java.util.HashMap;
import java.util.Map;

public class GuestRepository implements IGuestRepository {
    private final Map<String, Guest> sessions;

    public GuestRepository() {
        this.sessions = new HashMap<>();
    }

    @Override
    public void addGuest(String sessionId, Guest guest) {
        sessions.put(sessionId, guest);
    }

    @Override
    public void removeGuest(String sessionId) {
        sessions.remove(sessionId);
    }

    @Override
    public Guest getGuest(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public Map<String, Guest> getAllGuests() {
        return sessions;
    }
}
