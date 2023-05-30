package BusinessLayer;

import BusinessLayer.Logger.SystemLogger;
import ServiceLayer.DTOs.PositionDTO;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "store_owners")
public class StoreOwner implements Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private final Store store;
    @ManyToOne
    @JoinColumn(name = "store_owners")
    private final Member assigner;
    @Transient
    private final SystemLogger logger;

    public StoreOwner(Store store, Member assigner) {
        this.id = 0L; // Initializing with a default value
        this.store = store;
        this.assigner = assigner;
        logger = new SystemLogger();
    }

    @Override
    public Member getAssigner() {
        return assigner;
    }

    @Override
    public void setPositionOfMemberToStoreManager(Store store, Member member, Member assigner) throws Exception {
        member.setToStoreManager(store, assigner);
    }

    @Override
    public void setPositionOfMemberToStoreOwner(Store store, Member member, Member assigner) throws Exception {
        member.setToStoreOwner(store, assigner);
    }

    @Override
    public void setStoreManagerPermissions(Position storeManagerPosition, Set<PositionDTO.permissionType> permissions) {
        storeManagerPosition.setPermissions(permissions);
    }

    @Override
    public void addStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType newPermission) {
        storeManagerPosition.addPermission(newPermission);
    }

    @Override
    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType permission) {
        storeManagerPosition.removePermission(permission);

    }

    public Product addProduct(Store store, String productName, double price, String category, int quantity, String description) throws Exception {
        return store.addProduct(productName, price, category, quantity, description);
    }

    @Override
    public void editProductName(int productId, String newName) throws Exception {
        store.editProductName(productId, newName);
    }

    @Override
    public void editProductPrice(int productId, double newPrice) throws Exception {
        store.editProductPrice(productId, newPrice);
    }

    @Override
    public void editProductCategory(int productId, String newCategory) throws Exception {
        store.editProductCategory(productId, newCategory);
    }

    @Override
    public void editProductDescription(int productId, String newDescription) throws Exception {
        store.editProductDescription(productId, newDescription);
    }

    @Override
    public void removeProductFromStore(int productID) throws Exception {
        store.removeProduct(productID);
    }

    @Override
    public List<Purchase> getPurchaseHistory(Store store) {
        return store.getPurchaseList();
    }

    @Override
    public void addMinQuantityPurchasePolicy(int productId, int minQuantity, boolean allowNone) throws Exception {
        store.addMinQuantityPolicy(productId, minQuantity, allowNone);
    }

    @Override
    public void addMaxQuantityPurchasePolicy(int productId, int maxQuantity) throws Exception {
        store.addMaxQuantityPolicy(productId, maxQuantity);
    }

    @Override
    public void addProductTimeRestrictionPurchasePolicy(int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        store.addProductTimeRestrictionPolicy(productId, startTime, endTime);
    }

    @Override
    public void addCategoryTimeRestrictionPurchasePolicy(String category, LocalTime startTime, LocalTime endTime) throws Exception {
        store.addCategoryTimeRestrictionPolicy(category, startTime, endTime);
    }

    @Override
    public void joinPurchasePolicies(int policyId1, int policyId2, int operator) throws Exception {
        store.joinPolicies(policyId1, policyId2, operator);
    }

    @Override
    public void removePurchasePolicy(int policyId) throws Exception {
        store.removePolicy(policyId);
    }

    @Override
    public void addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception {
        store.addProductDiscount(productId, discountPercentage, compositionType);
    }

    @Override
    public void addCategoryDiscount(String category, double discountPercentage, int compositionType) throws Exception {
        store.addCategoryDiscount(category, discountPercentage, compositionType);
    }

    @Override
    public void addStoreDiscount(double discountPercentage, int compositionType) throws Exception {
        store.addStoreDiscount(discountPercentage, compositionType);
    }

    @Override
    public void removeDiscount(int discountId) throws Exception {
        store.removeDiscount(discountId);
    }

    @Override
    public void addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        store.addMinQuantityDiscountPolicy(discountId, productId, minQuantity, allowNone);
    }

    @Override
    public void addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity) throws Exception {
        store.addMaxQuantityDiscountPolicy(discountId, productId, maxQuantity);
    }

    @Override
    public void addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception {
        store.addMinBagTotalDiscountPolicy(discountId, minTotal);
    }

    @Override
    public void joinDiscountPolicies(int policyId1, int policyId2, int operator) throws Exception {
        store.joinDiscountPolicies(policyId1, policyId2, operator);
    }

    @Override
    public void removeDiscountPolicy(int policyId) throws Exception {
        store.removeDiscountPolicy(policyId);
    }

    @Override
    public void closeStore() throws IllegalAccessException {
        logger.error("This member hasn't permission to close store");
        throw new IllegalAccessException("This member hasn't permission to close store");
    }

    @Override
    public List<Member> getStoreEmployees() {
        return store.getEmployees();
    }

    @Override
    public void removeStoreOwner(Member storeOwnerToRemove, Guest m) throws Exception {
        if (!assigner.equals(m)) {
            logger.error(String.format("%s is not the assigner of %s", m.getUsername(), storeOwnerToRemove.getUsername()));
            throw new Exception("can remove only store owner assigned by him");
        }
        storeOwnerToRemove.notBeingStoreOwner(m, getStore());
    }

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public boolean hasPermission(PositionDTO.permissionType employeeList) {
        return true;
    }

    @Override
    public String getPositionName() {
        return "Owner";
    }

}
