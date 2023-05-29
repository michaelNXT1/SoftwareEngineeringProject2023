package BusinessLayer.Repositories;

import BusinessLayer.Repositories.Interfaces.IStoreRepository;
import BusinessLayer.Store;

import java.util.HashMap;
import java.util.Map;

public class StoreRepository implements IStoreRepository {
    private final Map<Integer, Store> stores;

    public StoreRepository() {
        this.stores = new HashMap<>();
    }

    @Override
    public void addStore(Store store) {
        stores.put(store.getStoreId(), store);
    }

    @Override
    public void removeStore(int storeId) {
        stores.remove(storeId);
    }

    @Override
    public Store getStore(int storeId) {
        return stores.get(storeId);
    }

    @Override
    public Map<Integer, Store> getAllStores() {
        return stores;
    }
    // Implement other methods as needed
}