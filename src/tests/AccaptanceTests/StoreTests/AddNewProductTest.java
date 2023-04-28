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
    public void addProductAsStoreOwnerSuccess(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",3.9,"milk",9,"10");
        assertTrue(addProductToStore(sessionID1,productID1,storeID,5));
    }

    @Test
    public void addProductAsStoreOwnerExistFail(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",3.9,"milk",9,"10");
        addProductToStore(sessionID1,productID1,storeID,5);
        assertFalse(addProductToStore(sessionID1,productID1,storeID,5));
    }

    @Test
    public void addProductAsStoreOwnerNotHisStoreFail(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",3.9,"milk",9,"10");
        logout(sessionID1);
        String sessionID2 = login("alon12", "alon0601");
        assertFalse(addProductToStore(sessionID2,productID1,storeID,5));
    }

    @Test
    public void addProductAsStoreOwnerNegativeAmountFail(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertFalse(addProductToStore(sessionID1,productID1,storeID,-3));
    }
}
