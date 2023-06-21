import AccaptanceTests.AccaptanceTests;
import AccaptanceTests.AppointTests.AppointTests;
import AccaptanceTests.GuestTest.GuestTest;
import AccaptanceTests.MemberTest.MemberTest;
import AccaptanceTests.StoreTests.StoreTests;
import AccaptanceTests.SystemTests.SystemTests;
import ConcurrencyTests.ConcurrencyTest;
import UnitTests.UnitTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TestRunner {
    public static Test suite(){
        TestSuite suite = new TestSuite("test runner integration");
        suite.addTest(AccaptanceTests.suite());
        suite.addTest(new TestSuite(ConcurrencyTest.class));
        suite.addTest(UnitTest.suite());
        return suite;
    }
}
