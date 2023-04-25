package AccaptanceTests.MemberTest;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class viewHist extends ServiceTests {

    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        register("alon12","alon593112@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }

    //USE CASES 3.7
    @Test
    public void testViewPurchaseHistory(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,"10");
        int productID2 = addProduct(storeID,"test2",3.9,"milk",9,"10");
        addToCart(storeID, productID1,1);
        addToCart(storeID, productID2,2);
        buyCart(1, "12345678", "04", "2021", "me", "777",
                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345");
        assertNotNull(viewPurchaseHistory(1,storeID));
    }

    //USE CASES 3.7
    @Test
    public void testViewPurchaseNotHisStoreHistory(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,"10");
        int productID2 = addProduct(storeID,"test2",3.9,"milk",9,"10");
        addToCart(storeID, productID1,1);
        addToCart(storeID, productID2,2);
        buyCart(1, "12345678", "04", "2021", "me", "777",
                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345");
        logout(1);
        login("alon12","alon593112@gmail.com", "alon0601");
        assertNotNull(viewPurchaseHistory(1,storeID));
    }
}
