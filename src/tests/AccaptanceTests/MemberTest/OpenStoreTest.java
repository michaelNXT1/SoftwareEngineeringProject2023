package AccaptanceTests.MemberTest;

import org.junit.Before;
import AccaptanceTests.ServiceTests;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenStoreTest extends ServiceTests{

    String sessionID1;

    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        sessionID1 = login("alon1","alon0601");
    }

    @Test
    public void testOpenStoreSuccessful(){
        assertTrue(openStore(sessionID1, "newee") > -1);
        assertTrue(openStore(sessionID1, "newwwww") > -1);
    }

    @Test
    public void testOpenStoreFailureNotLoggedIn(){
        logout(sessionID1);
        assertNull(openStore(sessionID1, "newee"));
        assertNull(openStore(sessionID1, "newee"));
    }
}
