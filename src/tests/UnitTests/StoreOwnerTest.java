package UnitTests;

import BusinessLayer.*;
import BusinessLayer.Member;
import ServiceLayer.DTOs.ProductDTO;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class StoreOwnerTest extends TestCase {
    //StoreManager storeManager;
    //StoreFounder storeFounder;
    Market market = new Market(null,true);
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
    @AfterEach
    public void tearDown() {
        market.clearAllData();
    }

    @Test

    public void testChangeStoreManagerPermissions() {
        StoreManager storeManager = new StoreManager(store,member,member);
        storeOwner.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        assertTrue(!storeManager.getPermissions().isEmpty());
        storeOwner.removeStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
    }
    @Test

    public void testRemoveProductFromStore() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        int productId = p.getProductId();
        storeOwner.removeProductFromStore(productId);
        assertFalse(store.getProducts().getAllProducts().contains(p));
    }
    @Test

    public void testEditProductName() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        int productId = p.getProductId();
        storeOwner.editProductName(productId, "Product 2");
        assertEquals(p.getProductName(),"Product 1");
    }
    @Test

    public void testEditProductPrice() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        int productId = p.getProductId();
        storeOwner.editProductPrice(productId, 60.0);
        assertEquals(p.getProductPrice(),10.0);
    }
    @Test

    public void testEditProductCategory() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        int productId = p.getProductId();
        storeOwner.editProductCategory(productId, "new category");
        assertEquals(p.getCategory(),"proxyProduct");
    }
    @Test

    public void testEditProductDescription() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        int productId = p.getProductId();
        storeOwner.editProductDescription(productId, "new description");
        assertEquals(p.getDescription(),"aa");
    }
    @Test

    public void testAddProduct() throws Exception {
        p = storeOwner.addProduct(store,"Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        assertEquals(p.getProductId(),store.getProduct(p.getProductId()).getProductId());
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