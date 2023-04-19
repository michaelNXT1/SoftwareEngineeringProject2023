package UnitTests;

import junit.framework.TestCase;
import org.example.BusinessLayer.*;
import org.junit.Test;

public class StoreManagerTest extends TestCase {
    StoreManager storeManager;
    //StoreFounder storeFounder;
    //StoreOwner storeOwner;
    Member member;
    Store store;
    public void setUp() throws Exception {
        super.setUp();
        member = new Member("member","member@post.bgu.ac.il","012");
        store = new Store(0,"store",member);
        storeManager = new StoreManager(store,member);
        //storeFounder = new StoreFounder(store);
        //storeOwner = new StoreOwner(store,member);
    }

    @Test
    void testAddStoreManagerPermissions() {
        try {
            storeManager.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
            assertTrue(storeManager.getPermissions().contains(StoreManager.permissionType.Inventory));;
        } catch (IllegalAccessException e) {
            fail("Unexpected IllegalAccessException was thrown");
        }
    }

    @Test
    void testAddStoreManagerPermissionsWithoutPermission() {
        try {
            StoreManager storeManager2 = new StoreManager(store,member);
            storeManager.addStoreManagerPermissions(storeManager2, StoreManager.permissionType.Inventory);
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to set storeManager's permissions", e.getMessage());
        }
    }

    public void testSetPositionOfMemberToStoreManager() {
    }

    public void testSetPositionOfMemberToStoreOwner() {
    }

    public void testRemoveProductFromStore() {
    }

    public void testEditProductName() {
    }

    public void testEditProductPrice() {
    }

    public void testEditProductCategory() {
    }

    public void testEditProductDescription() {
    }

    public void testAddProduct() {
    }

    public void testGetPurchaseHistory() {
    }

    public void testAddPermission() {
    }

    public void testRemovePermission() {
    }

    public void testCloseStore() {
    }
}