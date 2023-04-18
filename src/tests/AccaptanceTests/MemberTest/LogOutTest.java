package AccaptanceTests.MemberTest;


import AccaptanceTests.ServiceTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LogOutTest extends ServiceTests {

    @Before
    public void setUp(){
        login("hanamaru", "abc@gmail.com","12345");
    }


    @Test
    public void testLogoutSuccessful(){
        assertTrue(logout(1));
    }

    @Test
    public void testLogoutFailedNotLoggedIn(){
        logout(2);
    }


}
