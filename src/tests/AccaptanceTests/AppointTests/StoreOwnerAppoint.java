package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class StoreOwnerAppoint extends ServiceTests {
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        register("alon12","alon593112@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }
}
