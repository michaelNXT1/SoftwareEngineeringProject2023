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
        register("alon12", "alon0601");
        sessionID1 = login("alon1", "alon0601");

    }

    @Test
    public void testEditingManagerPremSuccess(){
        int storeID = openStore(sessionID1,"newStore");
        appointManager(sessionID1,storeID,"alon12");
        assertTrue(editManagerOptions(sessionID1,storeID,"alon12",1));
    }

    @Test
    public void testEditingManagerPremFail(){
        int storeID = openStore(sessionID1,"newStore");
        logout(sessionID1);
        String sessionID2 = login("alon12", "alon0601");
        assertFalse(editManagerOptions(sessionID2, storeID, "alon1",2));
    }
    @Test
    public void testEditingManagerPremFail2(){
        String sessionID2 = login("alon12","alon0601");
        int storeID = openStore(sessionID2, "newStore");
        assertFalse(editManagerOptions(sessionID2, storeID, "alon143",2));
    }

    @Test
    public void testAppointingAllReadyAManagerToManagerFail(){
        int storeID = openStore(sessionID1, "newStore2");
        assertFalse(appointManager(sessionID1, storeID, "alon1"));
    }
}
