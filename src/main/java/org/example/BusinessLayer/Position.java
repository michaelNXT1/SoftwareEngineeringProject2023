package org.example.BusinessLayer;

import java.util.List;

public interface Position {

    Store getStore();

    public void addStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType newPermission) throws IllegalAccessException;   //5.10

    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission) throws IllegalAccessException;   //5.10

    void setPositionOfMemberToStoreManager(Store store, Member member) throws Exception; //5.9

    void setPositionOfMemberToStoreOwner(Store store, Member member) throws Exception;   //5.8

    void removeProductFromStore(int productID) throws IllegalAccessException;    //5.3

    void editProductName(int productId, String newName) throws Exception;   //5.2

    void editProductPrice(int productId, int newPrice) throws Exception;    //5.2

    void editProductCategory(int productId, String newCategory) throws Exception;    //5.2

    void editProductDescription(int productId, String newDescription) throws Exception;    //5.2

    Product addProduct(Store store, String productName, double price, String category, double rating, int quantity) throws Exception; //5.1

    List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException;   //4.1


    default void addPermission(StoreManager.permissionType newPermission) {
    }

    default void removePermission(StoreManager.permissionType permission) {
    }

    public void closeStore() throws IllegalAccessException;

    public List<Member> getStoreEmployees() throws IllegalAccessException;
}
