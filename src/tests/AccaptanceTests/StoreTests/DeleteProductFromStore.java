package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class DeleteProductFromStore extends ServiceTests {
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        register("alon12", "alon0601");
        sessionID1 = login("alon1", "alon0601");

    }

    @Test
    public void DeleteExistingProductSuccess(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",3.9,"milk",9,"10");
        assertTrue(deleteProduct(sessionID1, storeID, productID1));
    }

    @Test
    public void DeleteExistingProductFail(){
        int storeID = openStore(sessionID1,"newStore2");
        assertFalse(deleteProduct(sessionID1, storeID, 1));
    }

    @Test
    public void DeleteExistingProductNotHisStoreFail(){
        int storeID = openStore(sessionID1,"newStore3");
        logout(sessionID1);
        String sessionID2 = login("alon12", "alon0601");
        int productID1 = addProduct(sessionID2,storeID,"test",3.9,"milk",9,"10");
        assertFalse(editProductCategory(sessionID2,storeID, productID1, "test11"));
    }
}
