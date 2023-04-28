package AccaptanceTests.GuestTest;



import AccaptanceTests.ServiceTests;
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
        register("alon1","alon59311@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");
    }

    @After
    public void tearDown(){
        clearDB();

    }



//    @Test
//    public void testViewCartSuccessful(){
//        String cart = viewCart();
//       // assertEquals(cart, Database.Cart);
//        logout(Database.sessionId);
//        login(Database.sessionId, "hanamaru", "123456");
//        clearCart(Database.sessionId);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),1,5);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),2,5);
//        assertEquals(viewCart(Database.sessionId), Database.Cart);
//    }


    @Test
    public void testEditAmountInCartSuccessful(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,"10");
        int productID2 = addProduct(storeID,"test2",3.9,"milk",9,"10");
        addToCart(storeID,productID1, 5);
        addToCart(storeID, productID2,2);
        assertTrue(updateAmount(storeID, productID1,1));
    }

    @Test
    public void testEditAmountInCartNonPositiveAmountFailure(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,"10");
        assertFalse(updateAmount(storeID,productID1,-5));


    }

    @Test
    public void testEditAmountInCartProductNotInCartAmountFailure(){
        int storeID = openStore("newStore");
        assertFalse(updateAmount(storeID,1,3));
    }

    //USE CASE 2.7.3
    @Test
    public void testDeleteItemInCartSuccessful(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,"10");
        addToCart(storeID,productID1, 5);
        assertTrue(deleteItemInCart(storeID, productID1));
    }

    //USE CASE 2.7.4
    @Test
    public void testDeleteItemNotExistFail(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,"10");
        assertFalse(deleteItemInCart(storeID,productID1));
    }


}
