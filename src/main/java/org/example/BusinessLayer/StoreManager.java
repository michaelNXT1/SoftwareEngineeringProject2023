package org.example.BusinessLayer;

import java.util.List;

public class StoreManager  implements Position{

    enum permissionType{setPermissions}
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
    public void setPositionOfMemberToStoreManager(int storeID, String MemberToBecomeManager) {

    }

    @Override
    public void setPositionOfMemberToStoreOwner(int storeID, String MemberToBecomeOwner) {

    }

    @Override
    public void removeProductFromStore(int storeID, int productID) {

    }

    @Override
    public void editProductName(int storeID, int productID, String newName) {

    }

    @Override
    public void editProductPrice(int storeID, int productID, int newPrice) {

    }

    @Override
    public void editProductCategory(int storeID, int productID, String newCategory) {

    }

    @Override
    public void editProductDescription(int storeID, int productID, String newDescription) {

    }

    @Override
    public void addProduct(int storeID, int productID, String productName, int itemsAmount, int price) {

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
