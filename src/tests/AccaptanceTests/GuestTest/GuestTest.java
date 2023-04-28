package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import junit.framework.Test;
import junit.framework.TestSuite;

public class GuestTest extends ServiceTests {

    public static Test suite(){
        TestSuite suite = new TestSuite("guest user tests");
        suite.addTest(new TestSuite(RegistrationTest.class));
        suite.addTest(new TestSuite(LoginTests.class));
        suite.addTest(new TestSuite(SearchProductTests.class));
        suite.addTest(new TestSuite(AddToCartTests.class));
        suite.addTest(new TestSuite(CartTest.class));
        suite.addTest(new TestSuite(PurchaseTests.class));
        suite.addTest(new TestSuite(GetInfoTests.class));
        return suite;
    }
}