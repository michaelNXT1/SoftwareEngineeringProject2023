package AccaptanceTests.StoreTests;


import AccaptanceTests.ServiceTests;
import junit.framework.Test;
import junit.framework.TestSuite;

public class StoreTests extends ServiceTests {
    public static Test suite(){
        TestSuite suite = new TestSuite("Store tests");
        suite.addTest(new TestSuite(CloseStoreTest.class));
        suite.addTest(new TestSuite(AddNewProductTest.class));
        suite.addTest(new TestSuite(DeleteProductFromStore.class));
        suite.addTest(new TestSuite(EditExistingProduct.class));
        suite.addTest(new TestSuite(GetPositionInfo.class));
        suite.addTest(new TestSuite(PurchasePolicyTest.class));
        suite.addTest(new TestSuite(AddDiscountTest.class));
        return suite;
    }

}
