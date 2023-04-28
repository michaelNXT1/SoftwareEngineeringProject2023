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
    public void addProductAsStoreOwnerSuccess(){
        int storeID = openStore(sessionID1, "newStore");
        assertNotNull(showStorePositions(sessionID1,storeID));
    }

    @Test
    public void addProductAsStoreOwnerFail(){
        int storeID = openStore(sessionID1, "newStore");
        logout(sessionID1);
        String sessionID2 = login("alon12","alon0601");
        assertNull(showStorePositions(sessionID2,storeID));
    }

    @Test
    public void addProductAsStoreOwnerFail2(){
        assertNull(showStorePositions(sessionID1,3));
    }


}
