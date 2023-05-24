package BusinessLayer;

import java.time.LocalTime;
import java.util.List;

public interface Position {

    Store getStore();

    Member getAssigner();

    void addStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType newPermission) throws IllegalAccessException;   //5.10

    void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission) throws IllegalAccessException;   //5.10

    void setPositionOfMemberToStoreManager(Store store, Member member, Member assigner) throws Exception; //5.9

    void setPositionOfMemberToStoreOwner(Store store, Member member, Member assigner) throws Exception;   //5.8

    void removeProductFromStore(int productID) throws Exception;    //5.3

    void editProductName(int productId, String newName) throws Exception;   //5.2

    void editProductPrice(int productId, double newPrice) throws Exception;    //5.2

    void editProductCategory(int productId, String newCategory) throws Exception;    //5.2

    void editProductDescription(int productId, String newDescription) throws Exception;    //5.2

    Product addProduct(Store store, String productName, double price, String category, int quantity, String description) throws Exception; //5.1

    List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException;   //4.1

    void addMinQuantityPolicy(int productId, int minQuantity, boolean allowNone) throws Exception;

    void addMaxQuantityPolicy(int productId, int maxQuantity) throws Exception;

    void addProductTimeRestrictionPolicy(int productId, LocalTime startTime, LocalTime endTime) throws Exception;

    void addCategoryTimeRestrictionPolicy(String category, LocalTime startTime, LocalTime endTime) throws Exception;

    void joinPolicies(int policyId1, int policyId2, int operator) throws Exception;

    void removePolicy(int policyId) throws Exception;


    void addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception;

    void addCategoryDiscount(String category, double discountPercentage, int compositionType) throws Exception;

    void addStoreDiscount(double discountPercentage, int compositionType) throws Exception;

    void removeDiscount(int discountId) throws Exception;

    //Discount policies
    void addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception;

    void addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity) throws Exception;

    void addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception;

    void joinDiscountPolicies(int policyId1, int policyId2, int operator) throws Exception;

    void removeDiscountPolicy(int policyId) throws Exception;


    default void addPermission(StoreManager.permissionType newPermission) {
    }

    default void removePermission(StoreManager.permissionType permission) {
    }

    void closeStore() throws IllegalAccessException;

    List<Member> getStoreEmployees() throws IllegalAccessException;

    void removeStoreOwner(Member storeOwnerToRemove, Guest m) throws Exception;

    String getPositionName();
}
