package org.example.BusinessLayer;

import java.util.List;

public interface Position {

    Store getStore();

    public void addStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType newPermission) throws IllegalAccessException;   //5.10

    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission) throws IllegalAccessException;   //5.10

    void setPositionOfMemberToStoreManager(Store store, Member member) throws Exception; //5.9

    void setPositionOfMemberToStoreOwner(Store store, Member member) throws Exception;   //5.8

    void removeProductFromStore(Store store, int productID) throws IllegalAccessException;    //5.3

    void editProductName(Product p, String newName) throws IllegalAccessException;   //5.2

    void editProductPrice(Product p, int newPrice) throws IllegalAccessException;    //5.2

    void editProductCategory(Product p, String newCategory) throws IllegalAccessException;    //5.2

    void editProductDescription(Product p, String newDescription) throws IllegalAccessException;    //5.2

    Product addProduct(Store store, String productName, double price, String category, double rating, int quantity) throws Exception; //5.1

    List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException;   //4.1


    default void addPermission(StoreManager.permissionType newPermission) {
    }

    default void removePermission(StoreManager.permissionType permission) {
    }

    public void closeStore() throws IllegalAccessException;
}
