package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;

import org.example.BusinessLayer.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddToCartTests extends ServiceTests {

    /*
     * USE CASES 2.6
     *
     * */
    @Before
    public void setUp(){
        super.setUp();

        int sessionId = 1;
        int storeID = 1;


        login("hanamaru", "abc@gmail.com","12345");
        openStore("newStore");
        addProductToStore(sessionId, 1, storeID, 5);
        addProductToStore(sessionId, 2, storeID, 5);
        logout(sessionId);


    }

    @After
    public void tearDown(){
        clearDB();

//        Database.userToId.clear();
//        Database.userToStore.clear();
    }



    @Test
    public void testAddToCartSuccessful(){
        assertTrue(addToCart(1,1, 5));
        assertTrue(addToCart(1, 2, 5));
    }

    @Test
    public void testAddToCartFailure(){
        assertFalse(addToCart(1, 2, -2));
        assertFalse(addToCart(1, 2, 0));
    }
}