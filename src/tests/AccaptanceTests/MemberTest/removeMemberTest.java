package AccaptanceTests.MemberTest;

import AccaptanceTests.ServiceTests;

public class removeMemberTest extends ServiceTests {
    String sessionID1;

    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        sessionID1 = login("alon1","alon0601");
    }
    public void removeMemberSuccessful(){
        assertTrue(removeMember(sessionID1, ))
    }
