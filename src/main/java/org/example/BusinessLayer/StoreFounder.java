package org.example.BusinessLayer;

import java.util.List;

public class StoreFounder implements Position{

    private Store store;

    public StoreFounder(Store store) {
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
    public void setPositionOfMemberToStoreManager(Store store, Member member){
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
    public void editProductName(Store store, int productID, String newName) {
        store.editProductName(productID, newName);
    }

    @Override
    public void editProductPrice(Store store, int productID, int newPrice) {
        store.editProductPrice(productID, newPrice);
    }

    @Override
    public void editProductCategory(Store store, int productID, String newCategory) {
        store.editProductCategory(productID, newCategory);
    }

    @Override
    public void editProductDescription(Store store, int productID, String newDescription) {
        store.editProductDescription(productID, newDescription);
    }

    @Override
    public void addProduct(Store store, int productID, int itemsAmount) {
        store.addProduct(productID, itemsAmount);
    }

    @Override
    public List<Purchase> getPurchaseHistory(Store store) {
        return store.getPurchseList();
    }

}
