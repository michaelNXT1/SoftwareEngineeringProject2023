package org.example.BusinessLayer;

import java.util.List;

public interface Position {

    Store store = null;

    Store getStore();

    public void addStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType newPermission) throws IllegalAccessException;   //5.10
    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission) throws IllegalAccessException;   //5.10
    void setPositionOfMemberToStoreManager(Store store, Member member) throws IllegalAccessException; //5.9
    void setPositionOfMemberToStoreOwner(Store store, Member member) throws IllegalAccessException;   //5.8
    void removeProductFromStore(Store store, int productID) throws IllegalAccessException;    //5.3
    void editProductName(Store store, int productID, String newName) throws IllegalAccessException;   //5.2
    void editProductPrice(Store store, int productID, int newPrice) throws IllegalAccessException;    //5.2
    void editProductCategory(Store store, int productID, String newCategory) throws IllegalAccessException;    //5.2
    void editProductDescription(Store store, int productID, String newDescription) throws IllegalAccessException;    //5.2
    void addProduct(Store store, int productID, int itemsAmount) throws IllegalAccessException; //5.1
    List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException;   //4.1
    void logout();


    default void addPermission(StoreManager.permissionType newPermission){}

    default void removePermission(StoreManager.permissionType permission){}

}
