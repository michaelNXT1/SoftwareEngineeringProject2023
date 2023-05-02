package UnitTests;

import BusinessLayer.*;
import junit.framework.TestCase;

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
        storeOwner = new StoreOwner(store,member);
        p = null;
    }

    public void testChangeStoreManagerPermissions() {
        StoreManager storeManager = new StoreManager(store,member);
        storeOwner.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        assertTrue(storeManager.getPermissions().contains(StoreManager.permissionType.Inventory));
        storeOwner.removeStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
    }

    public void testRemoveProductFromStore() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.removeProductFromStore(productId);
        assertNull(store.getProduct(p.getProductId()));
    }

    public void testEditProductName() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.editProductName(productId, "Product 2");
        assertEquals(p.getProductName(),"Product 2");
    }

    public void testEditProductPrice() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.editProductPrice(productId, 60.0);
        assertEquals(p.getProductPrice(),60.0);
    }

    public void testEditProductCategory() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.editProductCategory(productId, "new category");
        assertEquals(p.getCategory(),"new category");
    }

    public void testEditProductDescription() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeOwner.editProductDescription(productId, "new description");
        assertEquals(p.getDescription(),Long.parseLong("new description"));
    }

    public void testAddProduct() throws Exception {
        p = storeOwner.addProduct(store,"Product 1", 10.0, "proxyProduct", 100,"aa");
        assertEquals(p,store.getProduct(p.getProductId()));
    }

    public void testGetPurchaseHistory() {
        assertTrue(storeOwner.getPurchaseHistory(store).isEmpty());
    }


    public void testCloseStore() throws IllegalAccessException {
        try {
            storeOwner.closeStore();
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to close store", e.getMessage());

        }
    }
}