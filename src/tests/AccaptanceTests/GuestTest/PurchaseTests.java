package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        sessionID1 = login("alon1","alon0601");
        storeID2 = openStore(sessionID1, "newStore3");
        productID2 = addProduct(sessionID1, storeID2,"test2",3.9,"milk",9,"1");
        addToCart(sessionID1, storeID2, productID2, 5);


    }

    @After
    public void tearDown(){
        clearDB();
    }



    @Test
    public void testPurchaseSuccessful(){
        assertTrue(buyCart(sessionID1, "12345678", "04", "2021", "me", "777",
                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
    }
    @Test
    public void testNotEngItemsQuantityFail(){
        int productID1 = addProduct(sessionID1, storeID2,"test4",3.9,"milk",9,"1");
        addToCart(sessionID1, storeID2, productID1, 40);
        assertFalse(buyCart(sessionID1, "12345678", "04", "2021", "me", "777",
                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
    }
    @Test
    public void testBuyingPolicyFail(){
        addMinQuantityPolicy(sessionID1,storeID2,productID2,3,false);
        addToCart(sessionID1, storeID2, productID2, 2);
        assertFalse(buyCart(sessionID1, "12345678", "04", "2021", "me", "777",
                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
    }

    @Test
    public void testNothingInCartFail(){
        deleteItemInCart(sessionID1,storeID2,productID2);
        assertFalse(buyCart(sessionID1, "12345678", "04", "2021", "me", "777",
                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
    }

}
