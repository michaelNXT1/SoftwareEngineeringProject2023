package AccaptanceTests.MemberTest;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class RemoveMemberTest extends ServiceTests {
    String sessionID1;

    @Before
    public void setUp(){
        super.setUp();
        register("shoham", "sh20754");
        sessionID1 = loginSystemManager("alon1","alon0601");
    }

    public void testRemoveMemberSuccessful(){
        assertTrue(removeMember(sessionID1, "shoham"));
    }
    public void testRemoveMemberFailureNotSystemManager(){
        register("Efrat", "ef20754");
        String efratSessionID = login("Efrat", "ef20754");
        assertFalse(removeMember(efratSessionID, "shoham"));
    }
    public void testRemoveMemberFailureNotLoggedIn(){
        logoutSystemManager(sessionID1);
        assertFalse(removeMember(sessionID1, "shoham"));
    }
    public void testRemoveMemberFailureMemberNotExist(){
        removeMember(sessionID1, "shoham");
        assertFalse(removeMember(sessionID1, "shoham"));
    }
    public void testRemoveMemberFailureMemberHasPosition(){
        signUpSystemManager("michael123", "123546");
        String managerSession = loginSystemManager("michael123", "123546");
        assertFalse(removeMember(sessionID1, "michael123"));
    }



}