package AccaptanceTests.SystemTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class GetAllHistoryTests extends ServiceTests {
    String sessionID;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        sessionID = login("alon", "alon0601");

    }

    @Test
    public void testGetHistorySuccess(){
        assertTrue(getStoresPurchases(sessionID));
    }

    @Test
    public void testGetHistoryFail(){
        logout(sessionID);
        String sessionID2 = login("alon12", "alon0601");
        assertFalse(getStoresPurchases(sessionID2));
    }
    
}
