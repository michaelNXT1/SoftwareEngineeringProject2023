package org.example.BusinessLayer;

import java.util.List;

public class StoreManager  implements Position{

    public StoreManager(Store store) {
        this.store = store;
    }
    enum permissionType{setPermissions, setNewPosition, Inventory}
    private Store store;
    private List<permissionType> permissions;

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public void addStoreManagerPermissions(Position storeManagerPosition, permissionType newPermission) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setPermissions))
            storeManagerPosition.addPermission(newPermission);
        else {
            //TODO: return response
        }
    }

    @Override
    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setPermissions))
            storeManagerPosition.removePermission(Permission);
        else {
            //TODO: return response
        }
    }

    @Override
    public void setPositionOfMemberToStoreManager(Store store, Member member) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setNewPosition))
            member.setToStoreManager(store);
        else {
            //TODO: return response
        }
    }


    @Override
    public void setPositionOfMemberToStoreOwner(Store store, Member member) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setNewPosition))
            member.setToStoreManager(store);
        else {
            //TODO: return response
        }
    }

    @Override
    public void removeProductFromStore(Store store, int productID) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.removeProduct(productID);
        else {
            //TODO: return response
        }
    }

    @Override
    public void editProductName(Store store, int productID, String newName) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductName(productID, newName);
        else {
            //TODO: return response
        }
    }

    @Override
    public void editProductPrice(Store store, int productID, int newPrice) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductPrice(productID, newPrice);
        else {
            //TODO: return response
        }
    }

    @Override
    public void editProductCategory(Store store, int productID, String newCategory) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductCategory(productID, newCategory);
        else {
            //TODO: return response
        }
    }

    @Override
    public void editProductDescription(Store store, int productID, String newDescription) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductDescription(productID, newDescription);
        else {
            //TODO: return response
        }
    }

    public void addProduct(Store store, int productID, int itemsAmount) {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.addProduct(productID, itemsAmount);
        else {
            //TODO: return response
        }
    }
    @Override
    public List<Purchase> getPurchaseHistory(int storeID) {
        return null;
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
