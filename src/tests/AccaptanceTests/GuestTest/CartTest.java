package AccaptanceTests.GuestTest;



import AccaptanceTests.ServiceTests;
import org.example.BusinessLayer.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CartTest extends ServiceTests {

    /*
     * USE CASES 2.7.1-2.7.4
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

        addToCart(storeID,1, 5);
        addToCart(storeID, 1,2);

    }

    @After
    public void tearDown(){
        clearDB();

    }



    //USE CASE 2.7.1
//    @Test
//    public void testViewCartSuccessful(){
//        String cart = viewCart(Database.sessionId);
//       // assertEquals(cart, Database.Cart);
//        logout(Database.sessionId);
//        login(Database.sessionId, "hanamaru", "123456");
//        clearCart(Database.sessionId);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),1,5);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),2,5);
//        assertEquals(viewCart(Database.sessionId), Database.Cart);
//    }


    //USE CASE 2.7.2
//    @Test
//    public void testEditAmountInCartSuccessful(){
//        assertTrue(updateAmount(Database.sessionId, Database.userToStore.get("chika"),1, 3));
//        assertTrue(updateAmount(Database.sessionId, Database.userToStore.get("chika"),2, 5));
//    }

//    @Test
//    public void testEditAmountInCartNonPositiveAmountFailure(){
//        assertFalse(updateAmount(Database.sessionId, Database.userToStore.get("chika"),1,-5));
//
//
//    }

//    //USE CASE 2.7.3
//    @Test
//    public void testDeleteItemInCartSuccessful(){
//        assertTrue(deleteItemInCart(Database.sessionId, Database.userToStore.get("chika"),1));
//    }

//    //USE CASE 2.7.4
//    @Test
//    public void testDeleteAllCartSuccessful(){
//        assertTrue(clearCart(Database.sessionId));
//    }
//

}
