package org.example.BusinessLayer;

import java.util.List;

public interface Position {

    Store store = null;

    Store getStore();

    public void addStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType newPermission);   //5.10
    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission);   //5.10
    void setPositionOfMemberToStoreManager(Store store, Member member); //5.9
    void setPositionOfMemberToStoreOwner(int storeID, String MemberToBecomeOwner);
    void removeProductFromStore(int storeID, int productID);
    void editProductName(int storeID, int productID, String newName);
    void editProductPrice(int storeID, int productID, int newPrice);
    void editProductCategory(int storeID, int productID, String newCategory);
    void editProductDescription(int storeID, int productID, String newDescription);
    void addProduct(int storeID, int productID, String productName, int itemsAmount, int price);
    List<Purchase> getPurchaseHistory(int storeID);
    int openStore(String name);
    void logout();


    default void addPermission(StoreManager.permissionType newPermission){}

    default void removePermission(StoreManager.permissionType permission){}

}
