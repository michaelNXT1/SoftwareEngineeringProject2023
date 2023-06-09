package AccaptanceTests.MemberTest;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class viewHist extends ServiceTests {
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        register("alon12", "alon0601");
        sessionID1 = login("alon1","alon0601");

    }

    //USE CASES 3.7
    @Test
    public void testViewPurchaseHistory(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",4.5,"milk",9,"10");
        int productID2 = addProduct(sessionID1,storeID,"test2",3.9,"milk",9,"10");
        addToCart(sessionID1,storeID, productID1,1);
        addToCart(sessionID1,storeID, productID2,2);
        buyCart(sessionID1);
        assertNotNull(viewPurchaseHistory(sessionID1,storeID));
    }

    //USE CASES 3.7
    @Test
    public void testViewPurchaseNotHisStoreHistory(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",4.5,"milk",9,"10");
        int productID2 = addProduct(sessionID1,storeID,"test2",3.9,"milk",9,"10");
        addToCart(sessionID1,storeID, productID1,1);
        addToCart(sessionID1,storeID, productID2,2);
        buyCart(sessionID1);
        logout(sessionID1);
        String sessionID2 = login("alon12", "alon0601");
        assertNull(viewPurchaseHistory(sessionID2,storeID));
    }

    @Test
    public void testViewPurchaseStoreNotExist(){
        assertNull(viewPurchaseHistory(sessionID1,-1));
    }
}
