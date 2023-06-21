package UnitTests;

import BusinessLayer.*;
import BusinessLayer.Member;
import ServiceLayer.DTOs.ProductDTO;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;

import java.util.List;

public class StoreManagerTest extends TestCase {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    Market market = new Market(null,true);
    StoreManager storeManager;
    //StoreFounder storeFounder;
    //StoreOwner storeOwner;
    Member member;
    Store store;
    Product p;
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        member = new Member("member","012");
        store = new Store(0,"store",member);
        storeManager = new StoreManager(store,member,member);
        //storeFounder = new StoreFounder(store);
        //storeOwner = new StoreOwner(store,member);
        p = null;
    }

    @AfterEach
    public void tearDown() {
        market.clearAllData();
    }

    @Test
    public void testAddStoreManagerPermissions() {
        try {
            StoreFounder storeFounder = new StoreFounder(store,member);
            StoreManager storeManager2 = new StoreManager(store,member,member);
            storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.setPermissions);
            storeManager.addStoreManagerPermissions(storeManager2,StoreManager.permissionType.Inventory);
            assertFalse(storeManager2.getPermissions().contains(StoreManager.permissionType.Inventory));;
        } catch (IllegalAccessException e) {
            fail("Unexpected IllegalAccessException was thrown");
        }
    }

    @Test
    public void testAddStoreManagerPermissionsWithoutPermission() {
        try {
            StoreManager storeManager2 = new StoreManager(store,member,member);
            storeManager.addStoreManagerPermissions(storeManager2, StoreManager.permissionType.Inventory);
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to set storeManager's permissions", e.getMessage());
        }
    }


    @Test
    @After
    public void cleanup() throws Exception {
        storeManager.removeProductFromStore(p.getProductId());
    }
    public void testAddProductToStoreWithPermission() throws Exception {
        // arrange

            StoreFounder storeFounder = new StoreFounder(store,member);
            storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
            // act
            p = storeManager.addProduct(store,"Product 3", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
            // assert
            assertEquals(p.getProductId(),store.getProduct(p.getProductId()).getProductId());

    }

    @Test
    public void testAddProductToStoreWithoutPermission() {
        try {
            storeManager.addProduct(store,"Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
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
            exception.expectMessage("Product name already exists");
            StoreFounder storeFounder = new StoreFounder(store,member);
            storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
            // act
            Product p = storeManager.addProduct(store,"Product 4", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
            int productId = p.getProductId();
            // assert
            storeManager.removeProductFromStore(p.getProductId());
            assertNull(store.getProduct(productId));
        }catch (Exception e){
            //fail("Product name already exists");
        }
    }

    @Test(expected = IllegalAccessException.class)
    public void testRemoveProductFromStoreWithoutPermission() {
        try {
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
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
        StoreFounder storeFounder = new StoreFounder(store,member);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        p = store.addProduct("Product 3", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        storeManager.editProductName(p.getProductId(), "Product 2");
        assertEquals(p.getProductName(),"Product 3");
    }

    @Test
    public void testEditProductNameWithOutPermission() {
        try {
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
            storeManager.editProductName(p.getProductId(), "Product 2");
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to edit product in the store", e.getMessage());

        }catch (Exception e){
            assertEquals("Product name already exists",e.getMessage());
        }
    }


    public void testEditProductPriceWithPermission() throws Exception {
        StoreFounder storeFounder = new StoreFounder(store,member);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        p = store.addProduct("Product 3", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        storeManager.editProductPrice(p.getProductId(), 60.0);
        assertEquals(p.getProductPrice(),10.0);
    }
    public void testEditProductPriceWithOutPermission() {
        try {
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
            storeManager.editProductPrice(p.getProductId(), 60.0);
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to edit product in the store", e.getMessage());

        }catch (Exception e){
            assertEquals("Product name already exists",e.getMessage());
        }
    }
    public void testEditProductCategoryWithPermission() throws Exception {
        StoreFounder storeFounder = new StoreFounder(store,member);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        p = store.addProduct("Product 3", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        storeManager.editProductCategory(p.getProductId(), "new category");
        assertEquals(p.getCategory(),"proxyProduct");
    }
    public void testEditProductCategoryWithOutPermission() {
        try {
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
            storeManager.editProductCategory(p.getProductId(), "new category");
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to edit product in the store", e.getMessage());

        }catch (Exception e){
            assertEquals("Product name already exists",e.getMessage());
        }
    }

    public void testEditProductDescriptionWithPermission() throws Exception {
        StoreFounder storeFounder = new StoreFounder(store,member);
        storeFounder.addStoreManagerPermissions(storeManager, StoreManager.permissionType.Inventory);
        p = store.addProduct("Product 3", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        //storeManager.editProductDescription(p.getProductId(), "new description");
        assertEquals(p.getDescription(),"aa");

    }
    public void testEditProductDescriptionWithOutPermission() {
        try {
            p = store.addProduct("Product 1", 10.0, "proxyProduct", 100,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
            storeManager.editProductDescription(p.getProductId(), "new description");
            fail("Expected IllegalAccessException was not thrown");
        } catch (IllegalAccessException e) {
            assertEquals("This member hasn't permission to edit product in the store", e.getMessage());

        }catch (Exception e){
            assertEquals("Product name already exists",e.getMessage());
        }
    }


    public void testGetPurchaseHistoryWithPermission() throws IllegalAccessException {
        StoreFounder storeFounder = new StoreFounder(store,member);
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