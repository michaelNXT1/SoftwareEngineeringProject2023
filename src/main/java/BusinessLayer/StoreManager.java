package BusinessLayer;

import BusinessLayer.Logger.SystemLogger;
import ServiceLayer.DTOs.PositionDTO;
import ServiceLayer.DTOs.ProductDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class StoreManager implements Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "positionMember")
    private Member positionMember;
    public enum permissionType {
        setNewPosition, //setPositionOfMemberToStoreManager, setPositionOfMemberToStoreOwner - all actions of setting new position
        setPermissions, //addStoreManagerPermissions, removeStoreManagerPermissions - all actions refer to set another store manager permission
        Inventory, //removeProduct, addProduct, editProduct (Name, price, category, description)
        Purchases, // getPurchaseHistory
        EmployeeList // getStoreEmployees
    }
    @Transient //Marks a property or field as transient, indicating that it should not be persisted in the database.
    private SystemLogger logger = new SystemLogger();
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    @ManyToOne
    @JoinColumn(name = "assigner")
    private Member assigner;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "manager_permissions" ,joinColumns = @JoinColumn(name = "manager_id"))
    @Column(name = "permissions")
    private Set<permissionType> permissions;
    @Transient
    private Object permissionLock = new Object();

    public StoreManager(Store store, Member assigner,Member thisMember) {
        this.store = store;
        this.assigner = assigner;
        permissions = new HashSet<>();
        this.positionMember = thisMember;
    }

    public StoreManager() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setStore(Store store) {
        this.store = store;
    }

    public void setAssigner(Member assigner) {
        this.assigner = assigner;
    }


    public Object getPermissionLock() {
        return permissionLock;
    }

    public void setPermissionLock(Object permissionLock) {
        this.permissionLock = permissionLock;
    }

    @Override
    public Member getAssigner() {
        return assigner;
    }

    @Override
    public void setPositionOfMemberToStoreManager(Store store, Member member, Member assigner) throws Exception {
        //TODO: Check if he has appropriate access permission
        synchronized (permissionLock) {
            if (permissions.contains(permissionType.setNewPosition))
                member.setToStoreManager(store, assigner);
            else {
                logger.error("this store manager hasn't permission to set new position");
                throw new IllegalAccessException("This member hasn't permission to set new position");
            }
        }
    }

    @Override
    public void setPositionOfMemberToStoreOwner(Store store, Member member, Member assigner) throws Exception {
        //TODO: Check if he has appropriate access permission
        synchronized (permissionLock) {
            if (permissions.contains(permissionType.setNewPosition))
                member.setToStoreManager(store, assigner);
            else {
                logger.error("this store manager hasn't permission to set new position");
                throw new IllegalAccessException("This member hasn't permission to set new position");
            }
        }
    }

    @Override
    public void setStoreManagerPermissions(Position storeManagerPosition, Set<PositionDTO.permissionType> permissions) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        synchronized (permissionLock) {
            if (this.permissions.contains(permissionType.setPermissions))
                storeManagerPosition.setPermissions(permissions);
            else {
                logger.error("this store manager hasn't permission to set storeManager's permissions");
                throw new IllegalAccessException("This member hasn't permission to set storeManager's permissions");
            }
        }
    }

    @Override
    public void addStoreManagerPermissions(Position storeManagerPosition, permissionType newPermission) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        synchronized (permissionLock) {
            if (permissions.contains(permissionType.setPermissions))
                storeManagerPosition.addPermission(newPermission);
            else {
                logger.error("this store manager hasn't permission to set storeManager's permissions");
                throw new IllegalAccessException("This member hasn't permission to set storeManager's permissions");
            }
        }
    }

    @Override
    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        synchronized (permissionLock) {
            if (permissions.contains(permissionType.setPermissions))
                storeManagerPosition.removePermission(Permission);
            else {
                logger.error("this store manager hasn't permission to set storeManager's permissions");
                throw new IllegalAccessException("This member hasn't permission to set storeManager's permissions");
            }
        }
    }

    @Override
    public Product addProduct(Store store, String productName, double price, String category, int quantity, String description, ProductDTO.PurchaseType purchaseType) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            return store.addProduct(productName, price, category, quantity, description, purchaseType);
        else {
            logger.error("this store manager hasn't permission to add product to the store");
            throw new IllegalAccessException("This member hasn't permission to add product to the store");
        }
    }

    @Override
    public Product addAuctionProduct(Store s, String productName, Double price, String category, Integer quantity, String description, LocalDateTime auctionEndDateTime) throws Exception {
        if (permissions.contains(permissionType.Inventory))
            return store.addAuctionProduct(productName, price, category, quantity, description, auctionEndDateTime);
        else {
            logger.error("this store manager hasn't permission to add product to the store");
            throw new IllegalAccessException("This member hasn't permission to add product to the store");
        }
    }

    @Override
    public void editProductName(int productId, String newName) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductName(productId, newName);
        else {
            logger.error("this store manager hasn't permission to edit product in the store");
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductPrice(int productId, double newPrice) throws Exception {
        if (newPrice <= 0) {
            logger.error(String.format("the price %.02f is negative", newPrice));
            throw new Exception("the price of a product has to be negative");
        }
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductPrice(productId, newPrice);
        else {
            logger.error("this store manager hasn't permission to edit product in the store");
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductCategory(int productId, String newCategory) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductCategory(productId, newCategory);
        else {
            logger.error("this store manager hasn't permission to edit product in the store");
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductDescription(int productId, String newDescription) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductDescription(productId, newDescription);
        else {
            logger.error("this store manager hasn't permission to edit product in the store");
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void removeProductFromStore(int productID) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.removeProduct(productID);
        else {
            logger.error("this store manager hasn't permission to remove product from the store");
            throw new Exception("This member hasn't permission to remove product from the store");
        }
    }

    @Override
    public List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Purchases))
            return store.getPurchaseList();
        logger.error("this store manager hasn't permission to get the purchase's History");
        throw new IllegalAccessException("This member hasn't permission to get the purchase's History");
    }

    @Override
    public void addMinQuantityPurchasePolicy(int productId, int minQuantity, boolean allowNone) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addMaxQuantityPurchasePolicy(int productId, int maxQuantity) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addProductTimeRestrictionPurchasePolicy(int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addCategoryTimeRestrictionPurchasePolicy(String category, LocalTime startTime, LocalTime endTime) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void joinPurchasePolicies(int policyId1, int policyId2, int operator) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void removePurchasePolicy(int policyId) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public long addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public long addCategoryDiscount(String category, double discountPercentage, int compositionType) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public long addStoreDiscount(double discountPercentage, int compositionType) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void removeDiscount(int discountId) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public Integer addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public Integer addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public Integer addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void joinDiscountPolicies(int policyId1, int policyId2, int operator) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void removeDiscountPolicy(int policyId) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void setPermissions(Set<PositionDTO.permissionType> permissions) {
        for (PositionDTO.permissionType permission : permissions)
            this.permissions.add(permissionType.valueOf(permission.name()));
    }

    @Override
    public Set<PositionDTO.permissionType> getPermissions() {
        Set<PositionDTO.permissionType> ret = new HashSet<>();
        for (permissionType permission : permissions)
            ret.add(PositionDTO.permissionType.valueOf(permission.name()));
        return ret;
    }

    public void addPermission(StoreManager.permissionType newPermission) { //permission for store manager only
        permissions.add(newPermission);
    }

    @Override
    public void removePermission(permissionType permission) {
        permissions.remove(permission);
    }

    @Override
    public void closeStore() throws IllegalAccessException {
        logger.error("this store manager hasn't permission to close store");
        throw new IllegalAccessException("This member hasn't permission to close store");
    }

    @Override
    public List<Member> getStoreEmployees() throws IllegalAccessException {
        if (permissions.contains(permissionType.EmployeeList))
            return store.getEmployees();
        logger.error("this store manager hasn't permission to get the purchase's History");
        throw new IllegalAccessException("This member hasn't permission to get the purchase's History");
    }

    @Override
    public boolean removeStoreOwner(Member systemManagerToRemove, Guest m) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public boolean hasPermission(PositionDTO.permissionType employeeList) {
        return permissions.contains(permissionType.valueOf(employeeList.name()));
    }

    @Override
    public String getPositionName() {
        return "Manager";
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
        //TODO: Check if he has appropriate access permission
        synchronized (permissionLock) {
            if (permissions.contains(permissionType.setNewPosition))
                store.requestSetPositionOfMemberToStoreManager(positionMember, memberToBecomeManager);
            else {
                logger.error("this store manager hasn't permission to set new position");
                throw new IllegalAccessException("This member hasn't permission to set new position");
            }
        }
    }

    @Override
    public void requestSetPositionOfMemberToStoreOwner(Member memberToBecomeOwner) throws Exception {
        //TODO: Check if he has appropriate access permission
        synchronized (permissionLock) {
            if (permissions.contains(permissionType.setNewPosition))
                store.requestSetPositionOfMemberToStoreOwner(positionMember, memberToBecomeOwner);
            else {
                logger.error("this store manager hasn't permission to set new position");
                throw new IllegalAccessException("This member hasn't permission to set new position");
            }
        }
    }

    @Override
    public void rejectRequest(int requestId) throws Exception {
        //TODO: Check if he has appropriate access permission
        synchronized (permissionLock) {
            if (permissions.contains(permissionType.setNewPosition))
                store.rejectRequest(positionMember, requestId);
            else {
                logger.error("this store manager hasn't permission to set new position");
                throw new IllegalAccessException("This member hasn't permission to set new position");
            }
        }
    }

    @Override
    public void acceptRequest(int requestId) throws Exception {
        //TODO: Check if he has appropriate access permission
        synchronized (permissionLock) {
            if (permissions.contains(permissionType.setNewPosition))
                store.acceptRequest(positionMember, requestId);
            else {
                logger.error("this store manager hasn't permission to set new position");
                throw new IllegalAccessException("This member hasn't permission to set new position");
            }
        }
    }
}
