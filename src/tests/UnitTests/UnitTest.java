package UnitTests;

import AccaptanceTests.AppointTests.AppointTests;
import AccaptanceTests.GuestTest.*;
import AccaptanceTests.MemberTest.MemberTest;
import AccaptanceTests.StoreTests.StoreTests;
import AccaptanceTests.SystemTests.SystemTests;
import junit.framework.Test;
import junit.framework.TestSuite;

public class UnitTest {
    public static Test suite(){
        TestSuite suite = new TestSuite("unit tests");
        suite.addTest(new TestSuite(ExternalSystemsTest.class));
        suite.addTest(new TestSuite(MarketTest.class));
        suite.addTest(new TestSuite(ShoppingCartTest.class));
        suite.addTest(new TestSuite(StoreManagerTest.class));
        suite.addTest(new TestSuite(StoreOwnerTest.class));
        suite.addTest(new TestSuite(StoreFounderTest.class));
        suite.addTest(new TestSuite(StoreTests.class));
        return suite;
    }
}
