package org.example.BusinessLayer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreManager implements Position {

    enum permissionType {setPermissions, setNewPosition, Inventory, Purchases}

    private Store store;
    private Member assigner;
    private Set<permissionType> permissions;

    public StoreManager(Store store, Member assigner) {
        this.store = store;
        this.assigner = assigner;
        permissions = new HashSet<>();
    }

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public void addStoreManagerPermissions(Position storeManagerPosition, permissionType newPermission) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setPermissions))
            storeManagerPosition.addPermission(newPermission);
        else {
            throw new IllegalAccessException("This member hasn't permission to set storeManager's permissions");
        }
    }

    @Override
    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setPermissions))
            storeManagerPosition.removePermission(Permission);
        else {
            throw new IllegalAccessException("This member hasn't permission to set storeManager's permissions");
        }
    }

    @Override
    public void setPositionOfMemberToStoreManager(Store store, Member member) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setNewPosition))
            member.setToStoreManager(store);
        else {
            throw new IllegalAccessException("This member hasn't permission to set new position");
        }
    }

    @Override
    public void setPositionOfMemberToStoreOwner(Store store, Member member) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setNewPosition))
            member.setToStoreManager(store);
        else {
            throw new IllegalAccessException("This member hasn't permission to set new position");
        }
    }

    @Override
    public void removeProductFromStore(Store store, int productID) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.removeProduct(productID);
        else {
            throw new IllegalAccessException("This member hasn't permission to remove product from the store");
        }
    }

    @Override
    public void editProductName(Product p, String newName) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductName(p, newName);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductPrice(Product p, int newPrice) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductPrice(p, newPrice);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductCategory(Product p, String newCategory) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductCategory(p, newCategory);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductDescription(Product p, String newDescription) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductDescription(p, newDescription);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    public Product addProduct(Store store, String productName, double price, String category, double rating, int quantity) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            return store.addProduct(productName, price, category, rating, quantity);
        else {
            throw new IllegalAccessException("This member hasn't permission to add product to the store");
        }
    }

    @Override
    public List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Purchases))
            return store.getPurchaseList();
        throw new IllegalAccessException("This member hasn't permission to get the purchase's History");
    }

    public void addPermission(StoreManager.permissionType newPermission) { //permission for store manager only
        permissions.add(newPermission);
    }

    @Override
    public void removePermission(permissionType permission) {
        permissions.remove(permission);
    }

    @Override
    public void closeStore() throws IllegalAccessException {
        throw new IllegalAccessException("This member hasn't permission to close store");
    }
}
