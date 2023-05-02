package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;

public class StoreOwnerAppoint extends ServiceTests {
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon0601");
        register("alon12", "alon0601");
        login("alon1", "alon0601");

    }
}
