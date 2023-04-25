package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

public class LoginTests extends ServiceTests {
    /*
     * USE CASES 2.3
     *
     * */
    @Before
    public void setUp(){
        super.setUp();
        register("hanamaru", "abc@gmail.com","12345");
        register("chika", "abcd@gmail.com","12345");
    }

    @AfterAll
    public void tearDown(){
        clearDB();
    }

    @AfterEach
    public void tearD(){
        logout(1);
    }

    @Test
    public void testLoginSuccessful(){
        assertTrue(login("hanamaru", "abc@gmail.com","12345"));
    }

    @Test
    public void testLoginFailureWrongPassword(){
        assertFalse(login("hanamaru", "abc@gmail.com","12345657"));
    }

    @Test
    public void testLoginFailureNonExistingUser(){
        assertFalse(login("yohariko","abcd@gmail.com", "1234"));
    }


    @Test
    public void testLoginFailureAlreadyLoggedin(){
        login( "chika", "abcd@gmail.com","12345");
        assertFalse(login( "chika", "abcd@gmail.com","12345"));
    }

}
