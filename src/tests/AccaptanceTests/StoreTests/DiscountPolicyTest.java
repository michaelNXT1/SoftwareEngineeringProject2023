package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.PurchaseProductDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class DiscountPolicyTest extends ServiceTests {
    String sessionID1;

    @Before
    public void setUp() {
        super.setUp();
        register("alon1", "alon0601");
        register("alon12", "alon0601");
        sessionID1 = login("alon1", "alon0601");
        addPaymentMethod(sessionID1, "Credit","3","2019", "123");
    }

    @Test
    public void testAddMinQuantityDiscountPolicySuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        assertTrue(addMinQuantityDiscountPolicy(sessionID1, storeID, 0, productID1, 2, false));
    }

    @Test
    public void testAddMinQuantityPurchasePolicyFailure() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        assertFalse(addMinQuantityDiscountPolicy(sessionID1, storeID + 1, 0, productID1, 2, false));
        assertFalse(addMinQuantityDiscountPolicy(sessionID1, storeID, 1, productID1, 2, false));
        assertFalse(addMinQuantityDiscountPolicy(sessionID1, storeID, 0, productID1 + 1, 2, false));
        assertFalse(addMinQuantityDiscountPolicy(sessionID1, storeID, 0, productID1, -1, false));
    }

    @Test
    public void testPurchasePolicyWorksSuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 10.0, "milk", 9, "10");
        addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        addMinQuantityDiscountPolicy(sessionID1, storeID, 0, productID1, 2, false);
        addToCart(sessionID1, storeID, productID1, 2);
        PurchaseDTO purchase = buyCart(sessionID1);
        assertEquals(18.0, purchase.getTotalPrice());
    }

    @Test
    public void testPurchasePolicyWorksFailure() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 10.0, "milk", 9, "10");
        addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        addMinQuantityDiscountPolicy(sessionID1, storeID, 0, productID1, 2, false);
        addToCart(sessionID1, storeID, productID1, 1);
        PurchaseDTO purchase = buyCart(sessionID1);
        assertEquals(10.0, purchase.getTotalPrice());
    }
}
