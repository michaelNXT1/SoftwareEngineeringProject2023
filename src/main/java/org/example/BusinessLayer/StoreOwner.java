package org.example.BusinessLayer;

import java.util.List;

public class StoreOwner  implements Position {

    private Store store;

    public StoreOwner(Store store) {
        this.store = store;
    }

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public void addStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType newPermission) {
        storeManagerPosition.addPermission(newPermission);
    }

    @Override
    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType permission) {
        storeManagerPosition.removePermission(permission);

    }

    @Override
    public void setPositionOfMemberToStoreManager(Store store, Member member) {
        member.setToStoreManager(store);
    }


    @Override
    public void setPositionOfMemberToStoreOwner(Store store, Member member) {
        member.setToStoreOwner(store);
    }

    @Override
    public void removeProductFromStore(Store store, int productID) {
        store.removeProduct(productID);
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

}
