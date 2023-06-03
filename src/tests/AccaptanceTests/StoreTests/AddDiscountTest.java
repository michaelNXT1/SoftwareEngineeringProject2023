package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.PurchaseProductDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class AddDiscountTest extends ServiceTests {
    String sessionID1;

    @Before
    public void setUp() {
        super.setUp();
        register("alon1", "alon0601");
        register("alon12", "alon0601");
        sessionID1 = login("alon1", "alon0601");
        addPaymentMethod(sessionID1,"124","12","2026","540");
    }

    @Test
    public void testAddProductDiscountAsStoreOwnerSuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        assertTrue(addProductDiscount(sessionID1, storeID, productID1, 0.1, 0) != null);
    }

    @Test
    public void testAddProductDiscountAsStoreOwnerFailure() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        assertFalse(addProductDiscount(sessionID1, storeID, productID1 + 1, 0.1, 0) != null);
        assertFalse(addProductDiscount(sessionID1, storeID + 1, productID1, 0.1, 0)!= null);
        assertFalse(addProductDiscount(sessionID1, storeID, productID1, 0.0, 0)!=  null);
        assertFalse(addProductDiscount(sessionID1, storeID, productID1, 0.1, 5)!=  null);
    }

    @Test
    public void testAddCategoryDiscountAsStoreOwnerSuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        assertTrue(addCategoryDiscount(sessionID1, storeID, "milk", 0.1, 0));
    }

    @Test
    public void testAddCategoryDiscountAsStoreOwnerFailure() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        assertFalse(addCategoryDiscount(sessionID1, storeID, "mi;l", 0.1, 0));
        assertFalse(addCategoryDiscount(sessionID1, storeID + 1, "milk", 0.1, 0));
        assertFalse(addCategoryDiscount(sessionID1, storeID, "milk", 0.0, 0));
        assertFalse(addCategoryDiscount(sessionID1, storeID, "milk", 0.1, 5));
    }

    @Test
    public void testAddStoreDiscountAsStoreOwnerSuccess() {
        int storeID = openStore(sessionID1, "newStore");
        assertTrue(addStoreDiscount(sessionID1, storeID, 0.1, 0));
    }

    @Test
    public void testAddStoreDiscountAsStoreOwnerFailure() {
        int storeID = openStore(sessionID1, "newStore");
        assertFalse(addStoreDiscount(sessionID1, storeID + 1, 0.1, 0));
        assertFalse(addStoreDiscount(sessionID1, storeID, 0.0, 0));
        assertFalse(addStoreDiscount(sessionID1, storeID, 1.1, 0));
        assertFalse(addStoreDiscount(sessionID1, storeID, 0.1, 5));
    }

    @Test
    public void testDiscountWorks() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 10.0, "milk", 9, "10");
        addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        addToCart(sessionID1, storeID, productID1, 1);
        PurchaseDTO purchase = buyCart(sessionID1);
        PurchaseProductDTO p = purchase.getProductDTOList().get(0);
        assertEquals(9.0, p.getPrice());
    }
}
