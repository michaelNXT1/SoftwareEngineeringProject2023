package org.example.BusinessLayer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreManager implements Position {

    public enum permissionType {setPermissions, setNewPosition, Inventory, Purchases, EmployeeList}

    private Store store;
    private Member assigner;

    private Set<permissionType> permissions;

    public StoreManager(Store store, Member assigner) {
        this.store = store;
        this.assigner = assigner;
        permissions = new HashSet<>();
    }

    @Override
    public Store getStore() {
        return store;
    }
    public Set<permissionType> getPermissions() {
        return permissions;
    }

    @Override
    public void addStoreManagerPermissions(Position storeManagerPosition, permissionType newPermission) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setPermissions))
            storeManagerPosition.addPermission(newPermission);
        else {
            throw new IllegalAccessException("This member hasn't permission to set storeManager's permissions");
        }
    }

    @Override
    public void removeStoreManagerPermissions(Position storeManagerPosition, StoreManager.permissionType Permission) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setPermissions))
            storeManagerPosition.removePermission(Permission);
        else {
            throw new IllegalAccessException("This member hasn't permission to set storeManager's permissions");
        }
    }

    @Override
    public void setPositionOfMemberToStoreManager(Store store, Member member) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setNewPosition))
            member.setToStoreManager(store);
        else {
            throw new IllegalAccessException("This member hasn't permission to set new position");
        }
    }

    @Override
    public void setPositionOfMemberToStoreOwner(Store store, Member member) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.setNewPosition))
            member.setToStoreManager(store);
        else {
            throw new IllegalAccessException("This member hasn't permission to set new position");
        }
    }

    @Override
    public void removeProductFromStore(int productID) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.removeProduct(productID);
        else {
            throw new IllegalAccessException("This member hasn't permission to remove product from the store");
        }
    }

    @Override
    public void editProductName(int productId, String newName) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductName(productId, newName);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductPrice(int productId, double newPrice) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductPrice(productId, newPrice);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductCategory(int productId, String newCategory) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductCategory(productId, newCategory);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    @Override
    public void editProductDescription(int productId, long newDescription) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            store.editProductDescription(productId, newDescription);
        else {
            throw new IllegalAccessException("This member hasn't permission to edit product in the store");
        }
    }

    public Product addProduct(Store store, String productName, double price, String category, int quantity) throws Exception {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Inventory))
            return store.addProduct(productName, price, category, quantity);
        else {
            throw new IllegalAccessException("This member hasn't permission to add product to the store");
        }
    }

    @Override
    public List<Purchase> getPurchaseHistory(Store store) throws IllegalAccessException {
        //TODO: Check if he has appropriate access permission
        if (permissions.contains(permissionType.Purchases))
            return store.getPurchaseList();
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
        throw new IllegalAccessException("This member hasn't permission to close store");
    }

    @Override
    public List<Member> getStoreEmployees() throws IllegalAccessException {
        if (permissions.contains(permissionType.EmployeeList))
            return store.getEmployees();
        throw new IllegalAccessException("This member hasn't permission to get the purchase's History");
    }
}
