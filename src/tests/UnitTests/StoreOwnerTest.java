package UnitTests;

import junit.framework.TestCase;
import org.example.BusinessLayer.*;
import org.junit.jupiter.api.BeforeEach;

public class StoreOwnerTest extends TestCase {
    //StoreManager storeManager;
    //StoreFounder storeFounder;
    StoreOwner storeOwner;
    Member member;
    Store store;
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        member = new Member("member","member@post.bgu.ac.il","012");
        store = new Store(0,"store",member);
        Member member = new Member("member","member@post.bgu.ac.il","012");
        Store store = new Store(0,"store",member);
        //storeManager = new StoreManager(store,member);
        //storeFounder = new StoreFounder(store);
        storeOwner = new StoreOwner(store,member);
    }

    public void testChangeStoreManagerPermissions() {
        StoreManager storeManager = new StoreManager(store,member);
        storeOwner.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        assertTrue(storeManager.getPermissions().contains(StoreManager.permissionType.Inventory));
        storeOwner.removeStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
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