package org.example.BusinessLayer;

import java.util.List;

public interface Position {

    Store store = null;

    Store getStore();

    void addStoreManagerPermissions(String storeManager, int storeID, StoreManager.permissionType newPermission);

    void removeStoreManagerPermissions(String storeManager, int storeID, StoreManager.permissionType newPermission);

    void setPositionOfMemberToStoreManager(int storeID, String MemberToBecomeManager);
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





}
