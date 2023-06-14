package BusinessLayer.Repositories.Interfaces;


import BusinessLayer.Store;

import java.util.Map;

public interface IStoreRepository {
    void addStore(Store store);
    void removeStore(int storeId);
    Store getStore(int storeId);
    Map<Integer, Store> getAllStores();
    // Add other methods as needed
}
