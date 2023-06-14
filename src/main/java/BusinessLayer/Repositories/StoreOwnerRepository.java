package BusinessLayer.Repositories;

import BusinessLayer.Repositories.Interfaces.IStoreOwnerRepository;

import java.util.ArrayList;
import java.util.List;

public class StoreOwnerRepository implements IStoreOwnerRepository {
    private final List<String> storeOwners;

    public StoreOwnerRepository() {
        this.storeOwners = new ArrayList<>();
    }

    @Override
    public void addStoreOwner(String storeOwner) {
        storeOwners.add(storeOwner);
    }

    @Override
    public void removeStoreOwner(String storeOwner) {
        storeOwners.remove(storeOwner);
    }

    @Override
    public List<String> getAllStoreOwners() {
        return storeOwners;
    }
}
