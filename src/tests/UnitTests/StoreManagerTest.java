package UnitTests;

import junit.framework.TestCase;
import org.example.BusinessLayer.*;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class StoreManagerTest extends TestCase {
    StoreManager storeManager;
    //StoreFounder storeFounder;
    //StoreOwner storeOwner;
    Member member;
    Store store;
    Product p;
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        member = new Member("member","member@post.bgu.ac.il","012");
        store = new Store(0,"store",member);
        storeManager = new StoreManager(store,member);
        //storeFounder = new StoreFounder(store);
        //storeOwner = new StoreOwner(store,member);
        p = null;
    }

    @Test
    public void testAddStoreManagerPermissions() {
        try {
            StoreFounder storeFounder = new StoreFounder(store);
            StoreManager storeManager2 = new StoreManager(store,member);
            storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.setPermissions);
            storeManager.addStoreManagerPermissions(storeManager2,StoreManager.permissionType.Inventory);
            assertTrue(storeManager2.getPermissions().contains(StoreManager.permissionType.Inventory));;
        } catch (IllegalAccessException e) {
            fail("Unexpected IllegalAccessException was thrown");
        }
    }

    @Test
    public void testAddStoreManagerPermissionsWithoutPermission() {
        try {
            StoreManager storeManager2 = new StoreManager(store,member);
            storeManager.addStoreManagerPermissions(storeManager2, StoreManager.permissionType.Inventory);
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to set storeManager's permissions", e.getMessage());
        }
    }


    @Test
    @After
    public void cleanup() throws IllegalAccessException {
        storeManager.removeProductFromStore(p.getProductId());    }
    public void testAddProductToStoreWithPermission() throws Exception {
        // arrange

            StoreFounder storeFounder = new StoreFounder(store);
            storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
            // act
            p = storeManager.addProduct(store,"Product 1", 10.0, "proxyProduct", 100);
            // assert
            assertEquals(p,store.getProduct(p.getProductId()));

    }

    @Test
    public void testAddProductToStoreWithoutPermission() {
        try {
            storeManager.addProduct(store,"Product 1", 10.0, "proxyProduct", 100);
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to add product to the store", e.getMessage());
        }catch (Exception e){
            fail("Product name already exists");
        }
    }

    @Test
    public void testRemoveProductFromStoreWithPermission() {
        // arrange
        try {
            StoreFounder storeFounder = new StoreFounder(store);
            storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
            // act
            Product p = storeManager.addProduct(store,"Product 1", 10.0, "proxyProduct", 100);
            // assert
            storeManager.removeProductFromStore(p.getProductId());
            assertNull(store.getProduct(p.getProductId()));
        }catch (Exception e){
            fail("Product name already exists");
        }
    }

    @Test(expected = IllegalAccessException.class)
    public void testRemoveProductFromStoreWithoutPermission() {
        try {
            Product p = store.addProduct("Product 1", 10.0, "proxyProduct", 100);
            // act
            storeManager.removeProductFromStore(p.getProductId());
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to remove product from the store", e.getMessage());

        }catch (Exception e){
            assertEquals("Product name already exists",e.getMessage());
        }
    }

    @Test
    public void testEditProductNameWithPermission() throws Exception {
        StoreFounder storeFounder = new StoreFounder(store);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100);
        storeManager.editProductName(p.getProductId(), "Product 2");
        assertEquals(p.getProductName(),"Product 2");
    }

    @Test
    public void testEditProductNameWithOutPermission() {
        try {
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100);
            storeManager.editProductName(p.getProductId(), "Product 2");
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to edit product in the store", e.getMessage());

        }catch (Exception e){
            assertEquals("Product name already exists",e.getMessage());
        }
    }


    public void testEditProductPriceWithPermission() throws Exception {
        StoreFounder storeFounder = new StoreFounder(store);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100);
        storeManager.editProductPrice(p.getProductId(), 60.0);
        assertEquals(p.getProductPrice(),60.0);
    }
    public void testEditProductPriceWithOutPermission() {
        try {
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100);
            storeManager.editProductPrice(p.getProductId(), 60.0);
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to edit product in the store", e.getMessage());

        }catch (Exception e){
            assertEquals("Product name already exists",e.getMessage());
        }
    }
    public void testEditProductCategoryWithPermission() throws Exception {
        StoreFounder storeFounder = new StoreFounder(store);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100);
        storeManager.editProductCategory(p.getProductId(), "new category");
        assertEquals(p.getCategory(),"new category");
    }
    public void testEditProductCategoryWithOutPermission() {
        try {
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100);
            storeManager.editProductCategory(p.getProductId(), "new category");
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to edit product in the store", e.getMessage());

        }catch (Exception e){
            assertEquals("Product name already exists",e.getMessage());
        }
    }

    public void testEditProductDescriptionWithPermission() throws Exception {
        StoreFounder storeFounder = new StoreFounder(store);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        p = store.addProduct("Product 1", 10.0, "proxyProduct", 100);
        storeManager.editProductDescription(p.getProductId(), Long.parseLong("new description"));
        assertEquals(p.getDescription(),Long.parseLong("new description"));

    }
    public void testEditProductDescriptionWithOutPermission() {
        try {
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100);
            storeManager.editProductDescription(p.getProductId(), Long.parseLong("new description"));
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to edit product in the store", e.getMessage());

        }catch (Exception e){
            assertEquals("Product name already exists",e.getMessage());
        }
    }


    public void testGetPurchaseHistoryWithPermission() throws IllegalAccessException {
        StoreFounder storeFounder = new StoreFounder(store);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Purchases);
        List<Purchase> purchaseList = storeManager.getPurchaseHistory(store);
        assertTrue(purchaseList.isEmpty());
    }
    public void testGetPurchaseHistoryWithOutPermission() {
        try {
            storeManager.getPurchaseHistory(store);
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to get the purchase's History", e.getMessage());

        }
    }

    public void testCloseStore() {
        try {
            storeManager.closeStore();
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to close store", e.getMessage());

        }
    }
}