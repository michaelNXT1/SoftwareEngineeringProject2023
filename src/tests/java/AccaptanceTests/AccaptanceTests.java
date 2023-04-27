package AccaptanceTests;

import AccaptanceTests.AppointTests.AppointTests;
import AccaptanceTests.MemberTest.MemberTest;
import AccaptanceTests.StoreTests.StoreTests;
import AccaptanceTests.SystemTests.SystemTests;
import junit.framework.Test;
import junit.framework.TestSuite;
import AccaptanceTests.GuestTest.GuestTest;

public class AccaptanceTests {
    public static Test suite(){
        TestSuite suite = new TestSuite("acceptance integration");
        suite.addTest(GuestTest.suite());
        suite.addTest(MemberTest.suite());
        suite.addTest(StoreTests.suite());
        suite.addTest(AppointTests.suite());
        suite.addTest(SystemTests.suite());
        return suite;
    }
}
