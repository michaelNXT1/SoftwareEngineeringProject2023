package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class CloseStoreTest extends ServiceTests {

    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        register("alon12","alon593112@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }

    @Test
    public void closeStoreSuccess(){
        int storeID = openStore("newStore");
        assertTrue(closeStore(storeID));
    }

    @Test
    public void closeStoreNotOwnerFail(){
        int storeID = openStore("newStore");
        logout(1);
        login("alon12","alon593112@gmail.com", "alon0601");
        assertFalse(closeStore(storeID));
    }

    @Test
    public void closeStoreNotExistFail(){
        assertFalse(closeStore(3));
    }
}
