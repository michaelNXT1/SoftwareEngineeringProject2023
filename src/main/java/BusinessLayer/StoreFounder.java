package BusinessLayer;

import BusinessLayer.Logger.SystemLogger;
import ServiceLayer.DTOs.PositionDTO;
import ServiceLayer.DTOs.ProductDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
public class StoreFounder implements Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;
    @ManyToOne
    @JoinColumn(name = "positionMember")
    private Member positionMember;
    @Transient //Marks a property or field as transient, indicating that it should not be persisted in the database.
    private SystemLogger logger;
    @OneToOne
    @JoinColumn(name = "assigner")
    private Member assigner = null;

    public StoreFounder(Store store, Member member) {
        this.store = store;
        this.logger = new SystemLogger();
        this.positionMember = member;
    }

    public StoreFounder() {
    }

    public void setAssigner(Member assigner) {
        this.assigner = assigner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Member getPositionMember() {
        return positionMember;
    }

    public void setPositionMember(Member positionMember) {
        this.positionMember = positionMember;
    }

    public SystemLogger getLogger() {
        return logger;
    }

    public void setLogger(SystemLogger logger) {
        this.logger = logger;
    }

    @Override
    public Member getAssigner() {
        return null;
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

    @Override
    public Product addProduct(Store store, String productName, double price, String category, int quantity, String description, ProductDTO.PurchaseType purchaseType) throws Exception {
        return store.addProduct(productName, price, category, quantity, description, purchaseType);
    }

    @Override
    public Product addAuctionProduct(Store s, String productName, Double price, String category, Integer quantity, String description, LocalDateTime auctionEndDateTime) throws Exception {
        return store.addAuctionProduct(productName, price, category, quantity, description, auctionEndDateTime);
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
    public long addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception {
        return store.addProductDiscount(productId, discountPercentage, compositionType);
    }

    @Override
    public long addCategoryDiscount(String category, double discountPercentage, int compositionType) throws Exception {
        return store.addCategoryDiscount(category, discountPercentage, compositionType);
    }

    @Override
    public long addStoreDiscount(double discountPercentage, int compositionType) throws Exception {
        return store.addStoreDiscount(discountPercentage, compositionType);
    }

    @Override
    public void removeDiscount(int discountId) throws Exception {
        store.removeDiscount(discountId);
    }

    @Override
    public Integer addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        return store.addMinQuantityDiscountPolicy(discountId, productId, minQuantity, allowNone);
    }

    @Override
    public Integer addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity) throws Exception {
        return store.addMaxQuantityDiscountPolicy(discountId, productId, maxQuantity);
    }

    @Override
    public Integer addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception {
        return store.addMinBagTotalDiscountPolicy(discountId, minTotal);
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
        store.setOpen(false);
    }


    @Override
    public List<Member> getStoreEmployees() {
        return store.getEmployees();
    }

    @Override
    public boolean removeStoreOwner(Member storeOwnerToRemove, Guest m) throws Exception {
        return storeOwnerToRemove.notBeingStoreOwner(m, getStore());
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
        return "Founder";
    }

    @Override
    public void rejectOffer(int offerId) throws Exception {
        store.rejectOffer(positionMember, offerId);
    }

    @Override
    public void acceptOffer(int offerId, PaymentSystemProxy paymentSystem, SupplySystemProxy supplySystem) throws Exception {
        store.acceptOffer(positionMember, offerId, paymentSystem, supplySystem);
    }
}
