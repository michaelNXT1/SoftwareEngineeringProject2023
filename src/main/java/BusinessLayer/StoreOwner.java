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
public class StoreOwner implements Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    @ManyToOne
    @JoinColumn(name = "positionMember")
    private Member positionMember;
    @ManyToOne
    @JoinColumn(name = "assigner")
    private Member assigner;
    @Transient
    private SystemLogger logger;

    public StoreOwner(Store store, Member assigner,Member thisMember) {
        this.store = store;
        this.assigner = assigner;
        logger = new SystemLogger();
        this.positionMember = thisMember;
    }

    public StoreOwner() {
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

    @Override
    public Member getPositionMember() {
        return positionMember;
    }

    @Override
    public void setPositionMember(Member positionMember) {
        this.positionMember = positionMember;
    }

    public void setAssigner(Member assigner) {
        this.assigner = assigner;
    }

    public SystemLogger getLogger() {
        return logger;
    }

    public void setLogger(SystemLogger logger) {
        this.logger = logger;
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
        logger.error("This member hasn't permission to close store");
        throw new IllegalAccessException("This member hasn't permission to close store");
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
        return "Owner";
    }

    @Override
    public void rejectOffer(int offerId) throws Exception {
        store.rejectOffer(positionMember, offerId);
    }

    @Override
    public void acceptOffer(int offerId, PaymentSystemProxy paymentSystem, SupplySystemProxy supplySystem) throws Exception {
        store.acceptOffer(positionMember, offerId, paymentSystem, supplySystem);
    }

    @Override
    public void confirmAuction(int productId, PaymentSystemProxy paymentSystem, SupplySystemProxy supplySystem) throws Exception {
        store.confirmAuction(productId, paymentSystem, supplySystem);
    }

    @Override
    public void requestSetPositionOfMemberToStoreManager(Member memberToBecomeManager) throws Exception {
        store.requestSetPositionOfMemberToStoreManager(positionMember, memberToBecomeManager);
    }

    @Override
    public void requestSetPositionOfMemberToStoreOwner(Member memberToBecomeOwner) throws Exception {
        store.requestSetPositionOfMemberToStoreOwner(positionMember, memberToBecomeOwner);
    }

    @Override
    public void rejectRequest(int requestId) throws Exception {
        store.rejectRequest(positionMember, requestId);
    }

    @Override
    public void acceptRequest(int requestId) throws Exception {
        store.acceptRequest(positionMember, requestId);
    }

}
