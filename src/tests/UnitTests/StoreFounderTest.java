package UnitTests;

import BusinessLayer.*;
import BusinessLayer.Member;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class StoreFounderTest extends TestCase {
    Market market = new Market(null,true);

    //StoreManager storeManager;
    StoreFounder storeFounder;
    Member member;
    Store store;
    Product p;
    //StoreOwner storeOwner;
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        member = new Member("member","012");
        store = new Store(0,"store",member);
        //storeManager = new StoreManager(store,member);
        storeFounder = new StoreFounder(store,member);
        //storeOwner = new StoreOwner(store,member);
        p = null;
    }
    @AfterEach
    public void tearDown() {
        market.clearAllData();
    }
    @Test
    public void testChangeStoreManagerPermissions() {
        //check for store founder
        StoreManager storeManager = new StoreManager(store,member,member);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        assertTrue(storeManager.getPermissions().contains(StoreManager.permissionType.Inventory));
        storeFounder.removeStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
    }
    @Test

    public void testRemoveProductFromStore() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"bb");
        int productId = p.getProductId();
        storeFounder.removeProductFromStore(productId);
        try{
            store.getProduct(productId);
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }
    @Test

    public void testEditProductName() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeFounder.editProductName(productId, "Product 2");
        assertEquals(p.getProductName(),"Product 2");
    }
    @Test

    public void testEditProductPrice() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeFounder.editProductPrice(productId, 60.0);
        assertEquals(p.getProductPrice(),60.0);
    }
    @Test

    public void testEditProductCategory() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa");
        int productId = p.getProductId();
        storeFounder.editProductCategory(productId, "new category");
        assertEquals(p.getCategory(),"new category");
    }
    @Test

    public void testEditProductDescription() throws Exception {
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"ddd");
        int productId = p.getProductId();
        storeFounder.editProductDescription(productId, "new description");
        assertEquals(p.getDescription(),"new description");
    }
    @Test

    public void testAddProduct() throws Exception {
        p = storeFounder.addProduct(store,"Product 1", 10.0, "proxyProduct", 100,"bbb");
        assertEquals(p,store.getProduct(p.getProductId()));
    }
    @Test

    public void testGetPurchaseHistory() {
        assertTrue(storeFounder.getPurchaseHistory(store).isEmpty());
    }

    @Test

    public void testCloseStore() throws IllegalAccessException {
        storeFounder.closeStore();
        assertFalse(store.isOpen());
    }
}