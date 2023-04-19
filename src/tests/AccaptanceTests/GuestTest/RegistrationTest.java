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
        assertTrue(register("yohane","alon@gmail.com", "1234"));
        assertTrue(register( "sarah", "alon123@gmail.com","12345"));
    }

    @Test
    public void testRegisterFailureExistingUsername() {
        assertFalse(register("yohane","alon@gmail.com", "1234"));
        assertFalse(register( "sarah", "alon123@gmail.com","12345"));
    }

    @Test
    public void testIllegalEmail(){
        assertFalse(register("aaa", "", "avbdsv"));
        assertFalse(register("aaa", "aaaaa", "avbdsv"));
        assertFalse(register("aaa", "aaa@ccc", "avbdsv"));
    }

}

