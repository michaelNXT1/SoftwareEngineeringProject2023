package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class GetInfoTests extends ServiceTests {

    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }

    @Test
    public void testShowStoreInfoExist(){
        int storeID1 = openStore("newStore");
        int storeID2 = openStore("newStore2");
        assertTrue(getStore(storeID1));
        assertTrue(getStore(storeID2));
    }

    @Test
    public void testShowStoreInfoNotExist(){
        assertFalse(getStore(4));
        assertFalse(getStore(5));
    }

    @Test
    public void testShowProductInfoExist(){
        int storeID2 = openStore("newStore3");
        int productID1 = addProduct(storeID2,"test",3.9,"milk",9,10);
        int productID2 = addProduct(storeID2,"test2",3.9,"milk",9,10);
        assertTrue(getProduct(productID1,storeID2));
        assertTrue(getProduct(productID2,storeID2));
    }

    @Test
    public void testShowProductInfoNotExist(){
        int storeID2 = openStore("newStore5");
        assertFalse(getProduct(4,storeID2));
        assertFalse(getProduct(5,storeID2));
    }
}
