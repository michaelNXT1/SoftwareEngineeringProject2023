package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class DeleteProductFromStore extends ServiceTests {
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        register("alon12","alon593112@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }

    @Test
    public void DeleteExistingProductSuccess(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,10);
        assertTrue(deleteProduct(1, storeID, productID1));
    }

    @Test
    public void DeleteExistingProductFail(){
        int storeID = openStore("newStore2");
        assertTrue(deleteProduct(1, storeID, 1));
    }

    @Test
    public void DeleteExistingProductNotHisStoreFail(){
        int storeID = openStore("newStore3");
        logout(1);
        login("alon12","alon593112@gmail.com", "alon0601");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,10);
        assertTrue(editProductCategory(storeID, productID1, "test11"));
    }
}
