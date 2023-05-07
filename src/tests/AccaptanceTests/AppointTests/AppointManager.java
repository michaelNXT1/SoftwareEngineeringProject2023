package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;


public class AppointManager extends ServiceTests {
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        register("alon12", "alon0601");
        sessionID1 = login("alon1","alon0601");

    }

    @Test
    public void testAppointingExistingUserToManagerSuccess(){
        int storeID = openStore(sessionID1,"newStore");
        assertTrue(appointManager(sessionID1, storeID, "alon12"));
    }

    @Test
    public void testAppointManagerWithoutPremToFail(){
        int storeID = openStore(sessionID1,"newStore");
        logout(sessionID1);
        String sessionID2 = login("alon12", "alon0601");
        boolean ans = appointManager(sessionID2, storeID, "alon1");
        assertFalse(ans);
    }

    @Test
    public void testAppointingNotExistingUserToManagerFail(){
        int storeID = openStore(sessionID1,"newStore2");
        assertFalse(appointManager(sessionID1, storeID, "alon123"));
    }

    @Test
    public void testAppointingAllReadyAManagerToManagerFail(){
        int storeID = openStore(sessionID1, "newStore2");
        assertFalse(appointManager(sessionID1, storeID, "alon1"));
    }
}
