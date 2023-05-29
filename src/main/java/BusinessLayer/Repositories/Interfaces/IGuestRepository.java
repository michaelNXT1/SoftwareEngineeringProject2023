package BusinessLayer.Repositories.Interfaces;

import BusinessLayer.Guest;

import java.util.Map;

public interface IGuestRepository {
    void addGuest(String sessionId, Guest guest);
    void removeGuest(String sessionId);
    Guest getGuest(String sessionId);
    Map<String, Guest> getAllGuests();
}
