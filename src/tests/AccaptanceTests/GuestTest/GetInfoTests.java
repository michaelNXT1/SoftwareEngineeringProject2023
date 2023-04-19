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
        int storeID = openStore("newStore");
    }

    @Test
    public void testShowStoreInfoExist(){
        assertTrue(openStore("newee") > -1);
        assertTrue(openStore("newwwww") > -1);
    }
}
