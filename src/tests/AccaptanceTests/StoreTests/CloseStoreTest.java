package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class CloseStoreTest extends ServiceTests {
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        register("alon12", "alon0601");
        sessionID1 = login("alon1", "alon0601");

    }

    @Test
    public void closeStoreSuccess(){
        int storeID = openStore(sessionID1,"newStore");
        assertTrue(closeStore(sessionID1,storeID));
    }

    @Test
    public void closeStoreNotOwnerFail(){
        int storeID = openStore(sessionID1,"newStore");
        logout(sessionID1);
        String sessionID2 = login("alon12", "alon0601");
        assertFalse(closeStore(sessionID2,storeID));
    }

    @Test
    public void closeStoreNotExistFail(){
        assertFalse(closeStore(sessionID1,34432));
    }
}
