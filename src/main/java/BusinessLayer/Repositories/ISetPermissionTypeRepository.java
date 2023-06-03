package BusinessLayer.Repositories;

import BusinessLayer.StoreManager;

import java.util.Set;

public interface ISetPermissionTypeRepository {
    void addPermission(StoreManager.permissionType permission);
    void removePermission(StoreManager.permissionType permission);
    Set<StoreManager.permissionType> getAllPermissions();

    boolean contains(StoreManager.permissionType permission);
}
