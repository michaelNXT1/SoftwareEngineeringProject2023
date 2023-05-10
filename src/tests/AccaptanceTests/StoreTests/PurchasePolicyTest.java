package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class PurchasePolicyTest extends ServiceTests {
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
    public void testAddMinQuantityPurchasePolicySuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        assertTrue(addMinQuantityPolicy(sessionID1, storeID, productID1, 2, false));
    }

    @Test
    public void testAddMinQuantityPurchasePolicyFailure() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        assertFalse(addMinQuantityPolicy(sessionID1, storeID+1, productID1, 2, false));
        assertFalse(addMinQuantityPolicy(sessionID1, storeID, productID1+1, 2, false));
        assertFalse(addMinQuantityPolicy(sessionID1, storeID, productID1, -1, false));
    }

    @Test
    public void testPurchasePolicyWorksSuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        addMinQuantityPolicy(sessionID1, storeID, productID1, 2, false);
        addToCart(sessionID1, storeID, productID1, 3);
        assertNotNull(buyCart(sessionID1));
    }

    @Test
    public void testPurchasePolicyWorksFailure() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        addMinQuantityPolicy(sessionID1, storeID, productID1, 2, false);
        addToCart(sessionID1, storeID, productID1, 1);
        assertNull(buyCart(sessionID1));
    }
}
