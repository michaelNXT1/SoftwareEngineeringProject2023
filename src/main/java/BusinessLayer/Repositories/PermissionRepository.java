package BusinessLayer.Repositories;

import BusinessLayer.Repositories.Interfaces.IPermissionRepository;
import BusinessLayer.StoreManager;
import application.views.StoreManagementView;

import java.util.HashSet;
import java.util.Set;

public class PermissionRepository implements IPermissionRepository {
    private final Set<StoreManager.permissionType> permissions;

    public PermissionRepository() {
        this.permissions = new HashSet<>();
    }

    @Override
    public void addPermission(StoreManager.permissionType permission) {
        permissions.add(permission);
    }

    @Override
    public void removePermission(StoreManager.permissionType permission) {
        permissions.remove(permission);
    }

    @Override
    public Set<StoreManager.permissionType> getAllPermissions() {
        return new HashSet<>(permissions);
    }
}
