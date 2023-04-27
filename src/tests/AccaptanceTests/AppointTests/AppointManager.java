package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class AppointManager extends ServiceTests {
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        register("alon12","alon593112@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }

    @Test
    public void appointingExistingUserToManagerSuccess(){
        int storeID = openStore("newStore");
        assertTrue(appointManager(1, storeID, "alon12"));
    }

    @Test
    public void appointManagerWithoutPremToFail(){
        int storeID = openStore("newStore");
        logout("1");
        login("alon12","alon593112@gmail.com", "alon0601");
        assertFalse(appointManager(1, storeID, "alon1"));
    }

    @Test
    public void appointingNotExistingUserToManagerFail(){
        int storeID = openStore("newStore2");
        assertTrue(appointManager(1, storeID, "alon123"));
    }

    @Test
    public void appointingAllReadyAManagerToManagerFail(){
        int storeID = openStore("newStore2");
        assertTrue(appointManager(1, storeID, "alon1"));
    }
}
