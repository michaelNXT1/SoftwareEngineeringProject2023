package org.example.BusinessLayer;

import java.util.List;

public interface Position {

    Store store = null;

    Store getStore();

    public void addStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType newPermission);   //5.10
    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission);   //5.10
    void setPositionOfMemberToStoreManager(Store store, Member member); //5.9
    void setPositionOfMemberToStoreOwner(Store store, Member member);   //5.8
    void removeProductFromStore(Store store, int productID);    //5.3
    void editProductName(Store store, int productID, String newName);   //5.2
    void editProductPrice(Store store, int productID, int newPrice);    //5.2
    void editProductCategory(Store store, int productID, String newCategory);    //5.2
    void editProductDescription(Store store, int productID, String newDescription);    //5.2
    void addProduct(int storeID, int productID, String productName, int itemsAmount, int price);
    List<Purchase> getPurchaseHistory(int storeID);
    int openStore(String name);
    void logout();


    default void addPermission(StoreManager.permissionType newPermission){}

    default void removePermission(StoreManager.permissionType permission){}

}
