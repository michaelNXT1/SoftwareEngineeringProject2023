package org.example.BusinessLayer;

import java.util.List;

public class StoreManager  implements Position{

    public StoreManager(Store store) {
        this.store = store;
    }
    enum permissionType{setPermissions, setNewPosition, Inventory, Purchases}
    private Store store;
    private List<permissionType> permissions;

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
    public void setPositionOfMemberToStoreManager(Store store, Member member) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setNewPosition))
            member.setToStoreManager(store);
        else {
            throw new IllegalAccessException("This member hasn't permission to set new position");
        }
    }


    @Override
    public void setPositionOfMemberToStoreOwner(Store store, Member member) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setNewPosition))
            member.setToStoreManager(store);
        else {
            throw new IllegalAccessException("This member hasn't permission to set new position");        }
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
    public void editProductName(Store store, int productID, String newName) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductName(productID, newName);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");        }
    }

    @Override
    public void editProductPrice(Store store, int productID, int newPrice) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductPrice(productID, newPrice);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductCategory(Store store, int productID, String newCategory) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductCategory(productID, newCategory);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductDescription(Store store, int productID, String newDescription) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductDescription(productID, newDescription);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    public void addProduct(Store store, int productID, int itemsAmount) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.addProduct(productID, itemsAmount);
        else {
            throw new IllegalAccessException("This member hasn't permission to add product to the store");
        }
    }
    @Override
    public List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Purchases))
            return store.getPurchseList();
        throw new IllegalAccessException("This member hasn't permission to get the purchase's History");
    }

    @Override
    public int openStore(String name) {
        return 0;
    }

    @Override
    public void logout() {

    }

    public void addPermission(StoreManager.permissionType newPermission) { //permission for store manager only
        permissions.add(newPermission);
    }

    @Override
    public void removePermission(permissionType permission) {
        permissions.remove(permission);
    }
}
