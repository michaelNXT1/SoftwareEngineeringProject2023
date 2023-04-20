package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;

import org.example.BusinessLayer.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddToCartTests extends ServiceTests {

    @Before
    public void setUp(){
        super.setUp();

        register("alon1","alon59311@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

        logout(1);


    }

    @After
    public void tearDown(){
        clearDB();

//        Database.userToId.clear();
//        Database.userToStore.clear();
    }



    @Test
    public void testAddToCartSuccessful(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,10);
        int productID2 = addProduct(storeID,"test2",3.9,"milk",9,10);
        assertTrue(addToCart(storeID, productID1, 5));
        assertTrue(addToCart(storeID, productID2, 5));
    }

    @Test
    public void testAddToCartAmountFailure(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,10);
        int productID2 = addProduct(storeID,"test2",3.9,"milk",9,10);
        assertFalse(addToCart(1, 2, -2));
        assertFalse(addToCart(1, 2, 0));
    }

    public void testAddToCartNoStoreFailure(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,10);
        int productID2 = addProduct(storeID,"test2",3.9,"milk",9,10);
        assertFalse(addToCart(2, productID1, 3));
        assertFalse(addToCart(2, productID2, 5));
    }

    public void testAddToCartNoProductFailure(){
        int storeID = openStore("newStore");
        assertFalse(addToCart(storeID, 2, 3));
        assertFalse(addToCart(storeID, 2, 5));
    }
}