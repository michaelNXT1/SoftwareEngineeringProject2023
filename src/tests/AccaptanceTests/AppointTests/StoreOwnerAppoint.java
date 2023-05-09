package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class StoreOwnerAppoint extends ServiceTests {
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
    public void testAppointMemberToOwnerSuccess(){
        int storeId = openStore(sessionId1,"newStore");
        assertTrue(appointOwner(sessionId1,storeId,"alon12"));
    }

    @Test
    public void testAppointMemberToOwnerNotStoreOwnerFAil(){
        int storeId = openStore(sessionId1,"newStore");
        logout(sessionId1);
        String sessionId2 = login("alon12", "alon0601");
        assertFalse(appointOwner(sessionId2,storeId,"alon123"));
    }

    @Test
    public void testAppointMemberToOwnerNotExistMemberFAil(){
        int storeId = openStore(sessionId1,"newStore");
        assertFalse(appointOwner(sessionId1,storeId,"alon1234"));
    }

    @Test
    public void testAppointMemberToOwnerAlreadyOwnerFAil(){
        int storeId = openStore(sessionId1,"newStore");
        assertFalse(appointOwner(sessionId1,storeId,"alon1"));
    }

}
