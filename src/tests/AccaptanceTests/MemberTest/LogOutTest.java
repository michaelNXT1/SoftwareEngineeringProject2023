package AccaptanceTests.MemberTest;


import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;


public class LogOutTest extends ServiceTests {
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        sessionID1 = login("alon1", "alon0601");
    }


    @Test
    public void testLogoutSuccessful(){
        assertTrue(logout(sessionID1));
    }

    @Test
    public void testLogoutFailedNotLoggedIn(){
        logout(sessionID1);
        assertFalse(logout(sessionID1));
    }


}
