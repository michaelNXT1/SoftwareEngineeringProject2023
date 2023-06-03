package Repositories;

import BusinessLayer.Guest;

import java.util.Map;

public interface IMapStringGuestRepository {

    void put(String key, Guest guest);
    Guest get(String key);
    void remove(String key);
    boolean containsKey(String key);
    boolean containsValue(Guest guest);
    Map<String, Guest> getAllGuests();
}
