package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;

public class GetMembersInformation extends ServiceTests {
    String sessionId1;
    int storeId;
    String storeOwnerToRemove;
    @Before
    public void setUp(){
        super.setUp();
        signUpSystemManager("alon12","alon0601");
        sessionId1 = login("alon12", "alon0601");
    }

    public void testGetMembersInformationSuccess(){
        assertTrue(getInformationAboutMembers(sessionId1));
    }

    public void testGetMembersInformationFailNotStoreOwner() {
        register("shoham", "sh20754");
        String sessionId = login("shoham", "sh20754");
        assertFalse(getInformationAboutMembers(sessionId)); //Member not has a position
    }
    public void testGetMembersInformationFailNotLoggedIn() {
        logout(sessionId1);
        assertFalse(getInformationAboutMembers(sessionId1)); //Member not has a position
    }

}
