package AccaptanceTests.SystemTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import junit.framework.TestCase;
import org.junit.Test;

public class GetAllHistoryTests extends ServiceTests {
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        login("alon","alon5931@gmail.com", "alon0601");

    }

    @Test
    public void getHistorySuccess(){
        assertTrue(getStoresPurchases());
    }

    @Test
    public void getHistoryFail(){
        logout(1);
        login("alon12","alon593112@gmail.com", "alon0601");
        assertFalse(getStoresPurchases());
    }
    
}
