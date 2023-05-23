package BusinessLayer;
import java.time.LocalTime;
import BusinessLayer.Logger.SystemLogger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreManager implements Position {

    private final SystemLogger logger = new SystemLogger();
    public enum permissionType {setPermissions, setNewPosition, Inventory, Purchases, EmployeeList}

    private final Store store;
    private final Member assigner;

    private final Set<permissionType> permissions;

    private final Object permissionLock = new Object();

    public StoreManager(Store store, Member assigner) {
        this.store = store;
        this.assigner = assigner;
        permissions = new HashSet<>();
    }

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public Member getAssigner() {
        return assigner;
    }

    public Set<permissionType> getPermissions() {
        return permissions;
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
        if(newPrice <= 0){
            logger.error(String.format("the price %.02f is negative",newPrice));
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
    public List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Purchases))
            return store.getPurchaseList();
        logger.error("this store manager hasn't permission to get the purchase's History");
        throw new IllegalAccessException("This member hasn't permission to get the purchase's History");
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
    public void removeStoreOwner(Member systemManagerToRemove, Guest m) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addMinQuantityPolicy(int productId, int minQuantity, boolean allowNone) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addMaxQuantityPolicy(int productId, int maxQuantity) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addProductTimeRestrictionPolicy(int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addCategoryTimeRestrictionPolicy(String category, LocalTime startTime, LocalTime endTime) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void joinPolicies(int policyId1, int policyId2, int operator) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void removePolicy(int policyId) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addProductDiscount(int productId, double discountPercentage, int compositionType) throws Exception {
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
    public void addMinQuantityDiscountPolicy(int discountId, int productId, int minQuantity, boolean allowNone) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addMaxQuantityDiscountPolicy(int discountId, int productId, int maxQuantity) throws Exception {
        logger.error("store manager hasn't permission to perform this action");
        throw new IllegalAccessException("This member hasn't permission to perform this action");
    }

    @Override
    public void addMinBagTotalDiscountPolicy(int discountId, double minTotal) throws Exception {
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
}
