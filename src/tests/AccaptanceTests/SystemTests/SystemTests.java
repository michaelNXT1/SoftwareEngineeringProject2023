package AccaptanceTests.SystemTests;

import AccaptanceTests.ServiceTests;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SystemTests extends ServiceTests {
    public static Test suite(){
        TestSuite suite = new TestSuite("System tests");
        suite.addTest(new TestSuite(GetAllHistoryTests.class));
        return suite;
    }
}
