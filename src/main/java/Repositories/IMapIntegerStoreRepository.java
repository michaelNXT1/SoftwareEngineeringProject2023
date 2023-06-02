package Repositories;

import BusinessLayer.Store;

import java.util.Map;

public interface IMapIntegerStoreRepository {
    void addStore(Integer key, Store store);
    void removeStore(Integer key);
    Store getStore(Integer key);
    Map<Integer, Store> getAllStores();
}
