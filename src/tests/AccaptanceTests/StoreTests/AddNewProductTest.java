package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class AddNewProductTest extends ServiceTests {
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        register("alon12", "alon0601");
        sessionID1 = login("alon1","alon0601");

    }
    @Test
    public void testAddProductAsStoreOwnerSuccess(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",3.9,"milk",9,"10");
        assertTrue(productID1 > 0);
    }



    @Test
    public void testAddProductAsStoreOwnerExistFail(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",3.9,"milk",9,"10");
        assertNull(addProduct(sessionID1,storeID,"test",3.9,"milk",9,"10"));
    }

    @Test
    public void testAddProductAsStoreOwnerNotHisStoreFail(){
        int storeID = openStore(sessionID1,"newStore");
        addProduct(sessionID1,storeID,"test",3.9,"milk",9,"10");
        logout(sessionID1);
        String sessionID2 = login("alon12", "alon0601");
        assertNull(addProduct(sessionID2,storeID,"test",3.9,"milk",9,"10"));
    }

    @Test
    public void testAddProductAsStoreOwnerNegativeAmountFail(){
        int storeID = openStore(sessionID1,"newStore");
        assertNull(addProduct(sessionID1, storeID,"test",3.9,"milk",-6,"10"));
    }
}
