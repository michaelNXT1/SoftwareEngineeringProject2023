package AccaptanceTests.MemberTest;

import org.example.BusinessLayer.Member;
import org.junit.Before;
import AccaptanceTests.ServiceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.Assertions.*;

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
