package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class RemoveStoreOwnerTest extends ServiceTests {
    String sessionId1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon0601");
        register("alon12", "alon0601");
        register("alon123", "alon0601");
        sessionId1 = login("alon1", "alon0601");
    }

    @Test
    public void testRemoveStoreOwnerSuccess(){

    }

    @Test
    public void testRemoveStoreOwnerFailNot(){

    }
}
