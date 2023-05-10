package AccaptanceTests.AppointTests;

import AccaptanceTests.ServiceTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AppointTests extends ServiceTests {
    public static Test suite(){
        TestSuite suite = new TestSuite("appoint tests");
        suite.addTest(new TestSuite(AppointManager.class));
        suite.addTest(new TestSuite(EditPrepositionsTest.class));
        suite.addTest(new TestSuite(RemoveStoreOwnerTest.class));
        suite.addTest(new TestSuite(GetMembersInformation.class));
   //     suite.addTest(new TestSuite(StoreOwnerAppoint.class));
        suite.addTest(new TestSuite(StoreOwnerAppoint.class));
        return suite;
    }
}
