package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class GetPositionInfo extends ServiceTests {
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        register("alon12", "alon0601");
        sessionID1 = login("alon1", "alon0601");

    }

    @Test
    public void testShowPositionsAsStoreOwnerSuccess(){
        int storeID = openStore(sessionID1, "newStore");
        assertTrue(showStorePositions(sessionID1,storeID) > 0);
    }

    @Test
    public void testShowPositionsNotAsStoreOwnerFail(){
        int storeID = openStore(sessionID1, "newStore");
        logout(sessionID1);
        String sessionID2 = login("alon12","alon0601");
        assertNull(showStorePositions(sessionID2,storeID));
    }

    @Test
    public void testShowPositionsNotLogInFail(){
        assertNull(showStorePositions(sessionID1,3));
    }


}
