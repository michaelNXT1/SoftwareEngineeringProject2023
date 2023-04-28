package AccaptanceTests.MemberTest;

import org.junit.Before;
import AccaptanceTests.ServiceTests;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenStoreTest extends ServiceTests{


    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");
    }

    @Test
    public void testOpenStoreSuccessful(){
        assertTrue(openStore("newee") > -1);
        assertTrue(openStore("newwwww") > -1);
    }

    @Test
    public void testOpenStoreFailureNotLoggedIn(){
        logout("1");
        assertNull(openStore("newee"));
        assertNull(openStore("newee"));
    }
}
