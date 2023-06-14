package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class EditPrepositionsTest extends ServiceTests {
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon0601");
        register("alon123", "alon0601");
        sessionID1 = login("alon1", "alon0601");

    }

    @Test
    public void testEditingManagerPremSuccess(){
        int storeID = openStore(sessionID1,"newStore");
        appointManager(sessionID1,storeID,"alon123");
        assertTrue(editManagerOptions(sessionID1,storeID,"alon12",1));
    }

    @Test
    public void testEditingManagerPremNotHavePremFail(){
        int storeID = openStore(sessionID1,"newStore");
        logout(sessionID1);
        String sessionID2 = login("alon123", "alon0601");
        assertFalse(editManagerOptions(sessionID2, storeID, "alon1",2));
    }
    @Test
    public void testEditingManagerPremUserNotManagerFail(){
        String sessionID2 = login("alon123","alon0601");
        int storeID = openStore(sessionID2, "newStore");
        assertFalse(editManagerOptions(sessionID2, storeID, "alon143",2));
    }

    public void testEditingManagerPremInvalidPremFail(){
        String sessionID2 = login("alon123","alon0601");
        int storeID = openStore(sessionID2, "newStore");
        assertFalse(editManagerOptions(sessionID2, storeID, "alon143",-1));
    }
}
