package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class AddNewProductTest extends ServiceTests {
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        register("alon12","alon593112@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }

    @Test
    public void addProductAsStoreOwnerSuccess(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        assertTrue(addProductToStore(1,productID1,storeID,5));
    }

    @Test
    public void addProductAsStoreOwnerExistFail(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        addProductToStore(1,productID1,storeID,5);
        assertFalse(addProductToStore(1,productID1,storeID,5));
    }

    @Test
    public void addProductAsStoreOwnerNotHisStoreFail(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        logout("1");
        login("alon12","alon593112@gmail.com", "alon0601");
        assertFalse(addProductToStore(1,productID1,storeID,5));
    }

    @Test
    public void addProductAsStoreOwnerNegativeAmountFail(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        assertFalse(addProductToStore(1,productID1,storeID,-3));
    }
}
