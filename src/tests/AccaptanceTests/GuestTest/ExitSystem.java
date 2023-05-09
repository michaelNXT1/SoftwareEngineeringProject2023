package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class ExitSystem extends ServiceTests {
    /*
     * USE CASES 2.3
     *
     * */
    String sessionId;
    @BeforeAll
    public void setUp(){
        super.setUp();
        sessionId = enterMarket();
    }

    @AfterAll
    public void tearDown(){
        clearDB();
    }

    @Test
    public void testExitSuccessfully(){
        assertTrue(exitMarket(sessionId));
    }

    @Test
    public void testExitSystemWhenNoBodyIn(){
        exitMarket(sessionId);
        assertTrue(!exitMarket(sessionId));
    }
}
