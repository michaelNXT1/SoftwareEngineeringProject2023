package Repositories;

import BusinessLayer.StoreOwner;

import java.util.List;

public interface IStoreOwnerRepository {
    void addStoreOwner(StoreOwner storeOwner);
    void removeStoreOwner(String storeOwner);
    List<String> getAllStoreOwners();
}
