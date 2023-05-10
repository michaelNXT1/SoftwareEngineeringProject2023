package AccaptanceTests.GuestTest;



import AccaptanceTests.ServiceTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegistrationTest extends ServiceTests {
    /*
     * USE CASES 2.2
     *
     * */
    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown(){
        clearDB();

//        Database.userToId.clear();
//        Database.userToStore.clear();
    }


    @Test
    public void testRegisterSuccessful() {
        assertTrue(register("yohane", "1234"));
    }

    @Test
    public void testRegisterFailureAlreadyRegisteredUsername() {
        register("yohane", "1234");
        assertFalse(register("yohane", "1234"));
    }

    @Test
    public void testRegisterFailureInvalidUsername() {
        assertFalse(register(null, "1234"));
    }
    @Test
    public void testRegisterFailureInvalidPassword() {
        assertFalse(register("null", null));
    }
}

