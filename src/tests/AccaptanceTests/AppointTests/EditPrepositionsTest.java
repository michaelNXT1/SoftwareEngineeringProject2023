package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class EditPrepositionsTest extends ServiceTests {

    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        register("alon12","alon593112@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }

    @Test
    public void editingManagerPremSuccess(){
        int storeID = openStore("newStore");
        appointManager(1,storeID,"alon12");
        assertTrue(editManagerOptions(1,storeID,"alon12",1));
    }

    @Test
    public void editingManagerPremFail(){
        int storeID = openStore("newStore");
        logout("1");
        login("alon12","alon593112@gmail.com", "alon0601");
        assertFalse(editManagerOptions(1, storeID, "alon1",2));
    }
    @Test
    public void editingManagerPremFail2(){
        login("alon12","alon593112@gmail.com", "alon0601");
        int storeID = openStore("newStore");
        assertFalse(editManagerOptions(1, storeID, "alon143",2));
    }

    @Test
    public void appointingAllReadyAManagerToManagerFail(){
        int storeID = openStore("newStore2");
        assertTrue(appointManager(1, storeID, "alon1"));
    }
}
