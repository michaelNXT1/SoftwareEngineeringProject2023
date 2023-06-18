package BusinessLayer;

import ServiceLayer.DTOs.PositionDTO;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface Position {

    Member getAssigner();

    Member getPositionMember();
    void setPositionMember(Member positionMember);
    void setPositionOfMemberToStoreManager(Store store, Member member, Member assigner) throws Exception; //5.9

    void setPositionOfMemberToStoreOwner(Store store, Member member, Member assigner) throws Exception;   //5.8

    void setStoreManagerPermissions(Position storeManagerPosition, Set<PositionDTO.permissionType> permissions) throws IllegalAccessException;

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
    long addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception;

    long addCategoryDiscount(String category, double discountPercentage, int compositionType) throws Exception;

    long addStoreDiscount(double discountPercentage, int compositionType) throws Exception;

    void removeDiscount(int discountId) throws Exception;

    //Discount policy management
    Integer addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception;

    Integer addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity) throws Exception;

    Integer addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception;

    void joinDiscountPolicies(int policyId1, int policyId2, int operator) throws Exception;

    void removeDiscountPolicy(int policyId) throws Exception;

    //Permission management
    default void setPermissions(Set<PositionDTO.permissionType> permissions) {
    }

    default Set<PositionDTO.permissionType> getPermissions() {
        return null;
    }

    default void addPermission(StoreManager.permissionType newPermission) {
    }

    default void removePermission(StoreManager.permissionType permission) {
    }

    void closeStore() throws IllegalAccessException;

    List<Member> getStoreEmployees() throws IllegalAccessException;

    boolean removeStoreOwner(Member storeOwnerToRemove, Guest m) throws Exception;

    Store getStore();

    boolean hasPermission(PositionDTO.permissionType employeeList);

    String getPositionName();
}
