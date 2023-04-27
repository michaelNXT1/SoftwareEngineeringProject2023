package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import org.example.BusinessLayer.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseTests extends ServiceTests {
    /*
     * USE CASES 2.8.1-2.8.4
     *
     * */
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");
        int storeID2 = openStore("newStore3");
        int productID2 = addProduct(storeID2,"test2",3.9,"milk",9,"1");
        addToCart(storeID2, productID2, 5);


    }

    @After
    public void tearDown(){
        clearDB();
    }



    @Test
    public void testPurchaseSuccessful(){
        assertTrue(buyCart(1, "12345678", "04", "2021", "me", "777",
                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
    }

    @Test
    public void testNothingInCartSuccessful(){
        deleteItemInCart(0,0);
        assertFalse(buyCart(1, "12345678", "04", "2021", "me", "777",
                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
    }

//
//    @Test
//    public void testPurchaseFailedSupplySystem(){
//
//        setupSystem("No supplies", "Mock Config","");
//        Database.sessionId = startSession();
//
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),1, 5);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),2, 5);
//        assertFalse(buyCart(Database.sessionId, "12345678", "04", "2021", "me", "777",
//                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
//    }
//
//    @Test
//    public void testPurchaseFailedPaymentSystem(){
//
//        setupSystem("Mock Config", "No payments","");
//        Database.sessionId = startSession();
//
//        PaymentSystemProxy.testing = true;
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),1, 5);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),2, 5);
//        assertFalse(buyCart(Database.sessionId, "12345678", "04", "2021", "me", "777",
//                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
//    }
//
//
}
