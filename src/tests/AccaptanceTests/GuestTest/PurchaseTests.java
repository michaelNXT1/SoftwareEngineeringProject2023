package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import ServiceLayer.DTOs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class PurchaseTests extends ServiceTests {
    /*
     * USE CASES 2.8.1-2.8.4
     *
     * */
    int storeID2;
    int productID2;
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        register("alon123", "alon0601");
        sessionID1 = login("alon1","alon0601");
        storeID2 = openStore(sessionID1, "newStore3");
        productID2 = addProduct(sessionID1, storeID2,"test2",3.9,"milk",9,"1");
        addPaymentMethod(sessionID1,"124","12","2026","540");
        addSupplyDetails(sessionID1,"abc","abc","abc","abc","abc");
        addToCart(sessionID1, storeID2, productID2, 5);
    }

    @After
    public void tearDown(){
        clearDB();
    }



    @Test
    public void testPurchaseSuccessful(){
        boolean product1Exist = false;
        PurchaseDTO purchaseDTO= buyCart(sessionID1);
        List<PurchaseProductDTO> bagList =  purchaseDTO.getProductDTOList();
        for(PurchaseProductDTO productDTO:bagList) {
            if (productDTO.getProductName().equals("test2"))
                product1Exist = true;
        }
        assertTrue(product1Exist);
    }
    @Test
    public void testNotEngItemsQuantityFail(){
        int productID1 = addProduct(sessionID1, storeID2,"test4",3.9,"milk",9,"1");
        logout(sessionID1);
        String sessionId2 = login("alon123", "alon0601");
        addToCart(sessionId2, storeID2, productID2, 5);
        buyCart(sessionId2);
        logout(sessionId2);
        login("alon1", "alon0601");
        assertNull(buyCart(sessionID1));
    }
    @Test
    public void testAndPoliciesPurchaseSuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        addMaxQuantityDiscountPolicy(sessionID1, storeID, 0,productID1, 6);
        addMinBagTotalDiscountPolicy(sessionID1, storeID, 0, 1);
        logout(sessionID1);
        String sessionId2 = login("alon123", "alon0601");
        addPaymentMethod(sessionId2,"1234","06","2026","540");
        addSupplyDetails(sessionId2,"alon","ASd","asd","sad","asd");
        addToCart(sessionId2,storeID,productID1,5);
        PurchaseDTO purchaseDTO = buyCart(sessionId2);
        assertTrue(purchaseDTO.getTotalPrice() == 3.9*0.9*0.9);
    }

    @Test
    public void testOrPoliciesPurchaseSuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        int qdId = addMaxQuantityDiscountPolicy(sessionID1, storeID, 0,productID1, 6);
        int mbID = addMinBagTotalDiscountPolicy(sessionID1, storeID, 0, 1);
        joinPolicies(sessionID1,storeID,qdId,mbID,0);
        logout(sessionID1);
        String sessionId2 = login("alon123", "alon0601");
        addPaymentMethod(sessionId2,"1234","06","2026","540");
        addSupplyDetails(sessionId2,"alon","ASd","asd","sad","asd");
        addToCart(sessionId2,storeID,productID1,5);
        PurchaseDTO purchaseDTO = buyCart(sessionId2);
        double ans =  3.9*0.9;
        assertTrue(purchaseDTO.getTotalPrice() ==ans);
    }

    @Test
    public void testXOrPoliciesPurchaseSuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        long discouId = addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        int qdId = addMaxQuantityDiscountPolicy(sessionID1, storeID, (int) discouId,productID1, 6);
        int mbID = addMinBagTotalDiscountPolicy(sessionID1, storeID, (int) discouId, 1);
        joinPolicies(sessionID1,storeID,qdId,mbID,0);
        logout(sessionID1);
        String sessionId2 = login("alon123", "alon0601");
        addPaymentMethod(sessionId2,"1234","06","2026","540");
        addSupplyDetails(sessionId2,"alon","ASd","asd","sad","asd");
        addToCart(sessionId2,storeID,productID1,5);
        PurchaseDTO purchaseDTO = buyCart(sessionId2);
        double ans = 15.600000000000001;
        assertTrue(purchaseDTO.getTotalPrice() == ans);
    }
    @Test
    public void testAddMinBagTotalDiscountPolicyPurchaseSuccess() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        int productID2 = addProduct(sessionID1, storeID, "test1", 3.9, "milk", 9, "10");
        addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        addMinBagTotalDiscountPolicy(sessionID1, storeID, 0, 2);
        logout(sessionID1);
        String sessionId2 = login("alon123", "alon0601");
        addPaymentMethod(sessionId2,"1234","06","2026","540");
        addSupplyDetails(sessionId2,"alon","ASd","asd","sad","asd");
        addToCart(sessionId2,storeID,productID1,7);
        addToCart(sessionId2,storeID,productID2,3);
        PurchaseDTO purchaseDTO = buyCart(sessionId2);
        double ans =  3.9*0.9 + 3.9;
        assertTrue(purchaseDTO.getTotalPrice() == ans);
    }

    @Test
    public void testAddMinBagTotalDiscountPolicyPurchaseFail() {
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID, "test", 3.9, "milk", 9, "10");
        addProductDiscount(sessionID1, storeID, productID1, 0.1, 0);
        addMinBagTotalDiscountPolicy(sessionID1, storeID, 0, 6);
        logout(sessionID1);
        String sessionId2 = login("alon123", "alon0601");
        addPaymentMethod(sessionId2,"1234","06","2026","540");
        addSupplyDetails(sessionId2,"alon","ASd","asd","sad","asd");
        addToCart(sessionID1,storeID,productID1,5);
        PurchaseDTO purchaseDTO = buyCart(sessionId2);
        assertFalse(purchaseDTO.getTotalPrice() == 3.9*0.1);
    }
    @Test
    public void testBuyingPolicyFail(){
        addMinQuantityPolicy(sessionID1,storeID2,productID2,3,false);
        addToCart(sessionID1, storeID2, productID2, 2);
        assertNull(buyCart(sessionID1));
    }

    @Test
    public void testNothingInCartFail(){
        deleteItemInCart(sessionID1,storeID2,productID2);
        assertNull(buyCart(sessionID1));
    }

}
