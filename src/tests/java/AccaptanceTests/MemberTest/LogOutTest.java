package AccaptanceTests.MemberTest;


import AccaptanceTests.ServiceTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LogOutTest extends ServiceTests {

    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");
    }


    @Test
    public void testLogoutSuccessful(){
        assertTrue(logout("1"));
    }

    @Test
    public void testLogoutFailedNotLoggedIn(){
        logout("1");
        assertFalse(logout("1"));
    }


}
