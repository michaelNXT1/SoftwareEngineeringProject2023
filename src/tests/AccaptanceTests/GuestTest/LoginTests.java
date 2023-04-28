package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class LoginTests extends ServiceTests {
    /*
     * USE CASES 2.3
     *
     * */
    @BeforeAll
    public void setUp(){
        super.setUp();
        register("hanamaru", "12345");
        register("chika", "12345");
    }

    @AfterAll
    public void tearDown(){
        clearDB();
    }

    @AfterEach
    public void tearD(){
        logout("1");
    }

    @Test
    public void testLoginSuccessful(){
        assertNotNull(login("hanamaru", "12345"));
    }

    @Test
    public void testLoginFailureWrongPassword(){
        assertNull(login("hanamaru", "12345657"));
    }

    @Test
    public void testLoginFailureNonExistingUser(){
        assertNull(login("yohariko", "1234"));
    }


    @Test
    public void testLoginFailureAlreadyLoggedin(){
        String sessionID = login( "chika", "12345");
        assertNull(login( "chika", "12345"));
    }

}
