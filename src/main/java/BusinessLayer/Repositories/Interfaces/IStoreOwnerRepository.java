package BusinessLayer.Repositories.Interfaces;

import java.util.List;

public interface IStoreOwnerRepository {
    void addStoreOwner(String storeOwner);
    void removeStoreOwner(String storeOwner);
    List<String> getAllStoreOwners();
}
