package BusinessLayer.Repositories.Interfaces;

import BusinessLayer.StoreManager;

import java.util.Set;

public interface IPermissionRepository {
    void addPermission(StoreManager.permissionType permissionType);
    void removePermission(StoreManager.permissionType permissionType);
    Set<StoreManager.permissionType> getAllPermissions();
}
