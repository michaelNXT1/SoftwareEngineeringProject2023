package AccaptanceTests.MemberTest;
import AccaptanceTests.ServiceTests;
import junit.framework.Test;
import junit.framework.TestSuite;


public class MemberTest extends ServiceTests{

    public static Test suite(){
        TestSuite suite = new TestSuite("Member tests");
        suite.addTest(new TestSuite(LogOutTest.class));
        suite.addTest(new TestSuite(OpenStoreTest.class));
        suite.addTest(new TestSuite(viewHist.class));
        suite.addTest(new TestSuite(RemoveMemberTest.class));
        return suite;
    }
}
