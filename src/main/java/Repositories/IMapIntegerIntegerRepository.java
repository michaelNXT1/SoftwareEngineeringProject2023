package Repositories;

import java.util.Map;

public interface IMapIntegerIntegerRepository {
    void put(Integer key, Integer value);
    Integer get(Integer key);
    void remove(Integer key);
    boolean containsKey(Integer key);
    boolean containsValue(Integer value);
    Map<Integer, Integer> getAllItems();
}

