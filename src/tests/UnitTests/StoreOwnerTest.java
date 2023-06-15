package UnitTests;

import BusinessLayer.*;
import BusinessLayer.Member;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class StoreOwnerTest extends TestCase {
    //StoreManager storeManager;
    //StoreFounder storeFounder;
    StoreOwner storeOwner;
    Member member;
    Store store;
    Product p;
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        member = new Member("member","012");
        store = new Store(0,"store",member);
        Member member = new Member("member","012");
        Store store = new Store(0,"store",member);
        //storeManager = new StoreManager(store,member);
        //storeFounder = new StoreFounder(store);
        storeOwner = new StoreOwner(store,member,member);
        p = null;
    }
    @Test

    public void testChangeStoreManagerPermissions() {
        StoreManager storeManager = new StoreManager(store,member,member);
        storeOwner.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        assertTrue(storeManager.getPermissions().contains(StoreManager.permissionType.Inventory));
        storeOwner.removeStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
    }
    @Test

    public void testRemoveProductFromStore() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.removeProductFromStore(productId);
        assertNull(store.getProduct(p.getProductId()));
    }
    @Test

    public void testEditProductName() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.editProductName(productId, "Product 2");
        assertEquals(p.getProductName(),"Product 2");
    }
    @Test

    public void testEditProductPrice() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.editProductPrice(productId, 60.0);
        assertEquals(p.getProductPrice(),60.0);
    }
    @Test

    public void testEditProductCategory() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.editProductCategory(productId, "new category");
        assertEquals(p.getCategory(),"new category");
    }
    @Test

    public void testEditProductDescription() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.editProductDescription(productId, "new description");
        assertEquals(p.getDescription(),Long.parseLong("new description"));
    }
    @Test

    public void testAddProduct() throws Exception {
        p = storeOwner.addProduct(store,"Product 1", 10.0, "proxyProduct", 100,"aa");
        assertEquals(p,store.getProduct(p.getProductId()));
    }
    @Test

    public void testGetPurchaseHistory() {
        assertTrue(storeOwner.getPurchaseHistory(store).isEmpty());
    }

    @Test

    public void testCloseStore() throws IllegalAccessException {
        try {
            storeOwner.closeStore();
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to close store", e.getMessage());

        }
    }
}