package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class GetInfoTests extends ServiceTests {
    String sessionID;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        sessionID = login("alon1","alon0601");

    }

    @Test
    public void testShowStoreInfoExist(){
        int storeID1 = openStore(sessionID, "newStore");
        assertTrue(getStore(sessionID, storeID1));
    }

    @Test
    public void testShowStoreInfoNotExist(){
        assertFalse(getStore(sessionID,4));
    }

    @Test
    public void testShowProductInfoExist(){
        int storeID2 = openStore(sessionID,"newStore3");
        int productID1 = addProduct(sessionID,storeID2,"test",3.9,"milk",9,"10");
        assertTrue(getProduct(sessionID, productID1,storeID2));
    }

    @Test
    public void testShowProductInfoNotExist(){
        int storeID2 = openStore(sessionID,"newStore5");
        assertFalse(getProduct(sessionID,4,storeID2));
    }
}
