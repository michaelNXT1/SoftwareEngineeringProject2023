package org.example.BusinessLayer;

import java.util.List;

public class StoreFounder implements Position {

    private Store store;
    private Member assigner;

    public StoreFounder(Store store, Member assigner) {
        this.store = store;
        this.assigner = assigner;
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
    public void setPositionOfMemberToStoreManager(Store store, Member member) throws Exception {
        member.setToStoreManager(store);
    }

    @Override
    public void setPositionOfMemberToStoreOwner(Store store, Member member) throws Exception {
        member.setToStoreOwner(store);
    }

    @Override
    public void removeProductFromStore(Store store, int productID) {
        store.removeProduct(productID);
    }

    @Override
    public void editProductName(Product p, String newName) {
        store.editProductName(p, newName);
    }

    @Override
    public void editProductPrice(Product p, int newPrice) {
        store.editProductPrice(p, newPrice);
    }

    @Override
    public void editProductCategory(Product p, String newCategory) {
        store.editProductCategory(p, newCategory);
    }

    @Override
    public void editProductDescription(Product p, String newDescription) {
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


    @Override
    public void closeStore() throws IllegalAccessException {
        store.setOpen(false);
    }

}
