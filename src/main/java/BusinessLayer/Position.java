package BusinessLayer;

import ServiceLayer.DTOs.PositionDTO;

import java.time.LocalTime;
import java.util.List;

public interface Position {

    Member getAssigner();

    void setPositionOfMemberToStoreManager(Store store, Member member, Member assigner) throws Exception; //5.9

    void setPositionOfMemberToStoreOwner(Store store, Member member, Member assigner) throws Exception;   //5.8

    void addStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType newPermission) throws IllegalAccessException;   //5.10

    void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission) throws IllegalAccessException;   //5.10

    Product addProduct(Store store, String productName, double price, String category, int quantity, String description) throws Exception; //5.1

    void editProductName(int productId, String newName) throws Exception;   //5.2

    void editProductPrice(int productId, double newPrice) throws Exception;    //5.2

    void editProductCategory(int productId, String newCategory) throws Exception;    //5.2

    void editProductDescription(int productId, String newDescription) throws Exception;    //5.2

    void removeProductFromStore(int productID) throws Exception;    //5.3

    List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException;   //4.1

    //Purchase policy management
    void addMinQuantityPurchasePolicy(int productId, int minQuantity, boolean allowNone) throws Exception;

    void addMaxQuantityPurchasePolicy(int productId, int maxQuantity) throws Exception;

    void addProductTimeRestrictionPurchasePolicy(int productId, LocalTime startTime, LocalTime endTime) throws Exception;

    void addCategoryTimeRestrictionPurchasePolicy(String category, LocalTime startTime, LocalTime endTime) throws Exception;

    void joinPurchasePolicies(int policyId1, int policyId2, int operator) throws Exception;

    void removePurchasePolicy(int policyId) throws Exception;

    //Discount management
    void addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception;

    void addCategoryDiscount(String category, double discountPercentage, int compositionType) throws Exception;

    void addStoreDiscount(double discountPercentage, int compositionType) throws Exception;

    void removeDiscount(int discountId) throws Exception;

    //Discount policy management
    void addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception;

    void addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity) throws Exception;

    void addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception;

    void joinDiscountPolicies(int policyId1, int policyId2, int operator) throws Exception;

    void removeDiscountPolicy(int policyId) throws Exception;

    //Permission management
    void addPermission(StoreManager.permissionType newPermission);

    void removePermission(StoreManager.permissionType permission);

    void closeStore() throws IllegalAccessException;

    List<Member> getStoreEmployees() throws IllegalAccessException;

    void removeStoreOwner(Member storeOwnerToRemove, Guest m) throws Exception;

    Store getStore();

    boolean hasPermission(PositionDTO.permissionType employeeList);

    String getPositionName();
}
