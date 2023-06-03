package BusinessLayer;

import BusinessLayer.Logger.SystemLogger;
import DAOs.SetPermissionTypeDAO;
import Repositories.ISetPermissionTypeRepository;
import ServiceLayer.DTOs.PositionDTO;
import jakarta.persistence.*;

//import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "positions")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "position_type", discriminatorType = DiscriminatorType.STRING)
public class StoreManager implements Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum permissionType {
        setNewPosition, //setPositionOfMemberToStoreManager, setPositionOfMemberToStoreOwner - all actions of setting new position
        setPermissions, //addStoreManagerPermissions, removeStoreManagerPermissions - all actions refer to set another store manager permission
        Inventory, //removeProduct, addProduct, editProduct (Name, price, category, description)
        Purchases, // getPurchaseHistory
        EmployeeList // getStoreEmployees
    }
    @Column(name = "position_type", insertable = false, updatable = false, columnDefinition = "text")
    private String positionType;

    @Transient //Marks a property or field as transient, indicating that it should not be persisted in the database.
    private final SystemLogger logger = new SystemLogger();    private final Store store;
    @ManyToOne
    @JoinColumn(name = "store_owners")
    private final Member assigner;
    private ISetPermissionTypeRepository permissions;
    private final Object permissionLock = new Object();

    public StoreManager(Store store, Member assigner) {
        this.id = 0L; // Initializing with a default value
        this.store = store;
        this.assigner = assigner;
        permissions = new SetPermissionTypeDAO(new HashSet<>());
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
    public Product addProduct(Store store, String productName, double price, String category, int quantity, String description) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            return store.addProduct(productName, price, category, quantity, description);
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
    public Integer addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addCategoryDiscount(String category, double discountPercentage, int compositionType) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addStoreDiscount(double discountPercentage, int compositionType) throws Exception {
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
        this.permissions = new SetPermissionTypeDAO(new HashSet<>());
        for (PositionDTO.permissionType permission : permissions)
            this.permissions.addPermission(permissionType.valueOf(permission.name()));
    }

    @Override
    public Set<PositionDTO.permissionType> getPermissions() {
        Set<PositionDTO.permissionType> ret = new HashSet<>();
        for (permissionType permission : permissions.getAllPermissions())
            ret.add(PositionDTO.permissionType.valueOf(permission.name()));
        return ret;
    }

    public void addPermission(StoreManager.permissionType newPermission) { //permission for store manager only
        permissions.addPermission(newPermission);
    }

    @Override
    public void removePermission(permissionType permission) {
        permissions.removePermission(permission);
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
    public void removeStoreOwner(Member systemManagerToRemove, Guest m) throws Exception {
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
}
