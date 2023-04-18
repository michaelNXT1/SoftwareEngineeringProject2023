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
    public void editProductName(Store store, Product p, String newName) {
        store.editProductName(p, newName);
    }

    @Override
    public void editProductPrice(Store store, Product p, int newPrice) {
        store.editProductPrice(p, newPrice);
    }

    @Override
    public void editProductCategory(Store store, Product p, String newCategory) {
        store.editProductCategory(p, newCategory);
    }

    @Override
    public void editProductDescription(Store store, Product p, String newDescription) {
        store.editProductDescription(p, newDescription);
    }

    @Override
    public Product addProduct(Store store, String productName, double price, String category, double rating, int quantity) throws Exception {
        return store.addProduct(productName, price, category, rating, quantity);
    }

    @Override
    public List<Purchase> getPurchaseHistory(Store store) {
        return store.getPurchaseList();
    }

}
