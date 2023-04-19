package UnitTests;

import junit.framework.TestCase;
import org.example.BusinessLayer.*;

public class StoreFounderTest extends TestCase {
    //StoreManager storeManager;
    StoreFounder storeFounder;
    Member member;
    Store store;
    //StoreOwner storeOwner;
    public void setUp() throws Exception {
        super.setUp();
        member = new Member("member","member@post.bgu.ac.il","012");
        store = new Store(0,"store",member);
        //storeManager = new StoreManager(store,member);
        storeFounder = new StoreFounder(store);
        //storeOwner = new StoreOwner(store,member);
    }

    public void testChangeStoreManagerPermissions() {
        //check for store founder
        StoreManager storeManager = new StoreManager(store,member);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        assertTrue(storeManager.getPermissions().contains(StoreManager.permissionType.Inventory));
        storeFounder.removeStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
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