package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddToCartTests extends ServiceTests {

    String sessionID;
    @Before
    public void setUp(){
        super.setUp();

        register("alon1","alon0601");
        sessionID = login("alon1","alon0601");



    }

    @After
    public void tearDown(){
        clearDB();

//        Database.userToId.clear();
//        Database.userToStore.clear();
    }



    @Test
    public void testAddToCartSuccessful(){
        int storeID = openStore(sessionID, "newStore");
        int productID1 = addProduct(sessionID, storeID,"test",4.5,"milk",9,"10");
        int productID2 = addProduct(sessionID, storeID,"test2",3.9,"milk",9,"10");
        assertTrue(addToCart(sessionID, storeID, productID1, 5));
        assertTrue(addToCart(sessionID, storeID, productID2, 5));
    }

    @Test
    public void testAddToCartAmountFailure(){
        int storeID = openStore(sessionID, "newStore");
        int productID1 = addProduct(sessionID, storeID,"test",4.5,"milk",9,"sad");
        int productID2 = addProduct(sessionID, storeID,"test2",3.9,"milk",9,"10");
        assertFalse(addToCart(sessionID, storeID, productID1,-2));
    }

    public void testAddToCartNoStoreFailure(){
        int storeID = openStore(sessionID,"newStore");
        int productID1 = addProduct(sessionID,storeID,"test",4.5,"milk",9,"10");
        assertFalse(addToCart(sessionID,-1, productID1, 3));
    }

    public void testAddToCartNoProductFailure(){
        int storeID = openStore(sessionID,"newStore");
        assertFalse(addToCart(sessionID,storeID, 2, 3));
        assertFalse(addToCart(sessionID,storeID, 2, 5));
    }
}