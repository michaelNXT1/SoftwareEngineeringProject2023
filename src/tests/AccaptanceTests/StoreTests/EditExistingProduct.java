package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class EditExistingProduct extends ServiceTests {
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        register("alon12","alon0601");
        sessionID1 = login("alon1","alon0601");

    }

    @Test
    public void editNameExistingProductSuccess(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductName(sessionID1, storeID, productID1, "test11"));
    }

    @Test
    public void editCategoryExistingProductSuccess(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductCategory(sessionID1, storeID, productID1, "test11"));
    }

    @Test
    public void editPriceExistingProductSuccess(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1 ,storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductPrice(sessionID1, storeID, productID1, 1));
    }

    @Test
    public void editNameExistingProductFail(){
        int storeID = openStore(sessionID1, "newStore");
        assertTrue(editProductName(sessionID1, storeID, 1, "test11"));
    }

    @Test
    public void editCategoryExistingProductFail(){
        int storeID = openStore(sessionID1, "newStore");
        assertTrue(editProductCategory(sessionID1, storeID, 1, "test11"));
    }

    @Test
    public void editPriceExistingProductFail(){
        int storeID = openStore(sessionID1, "newStore");
        assertTrue(editProductPrice(sessionID1, storeID, 1, 1));
    }

    @Test
    public void editNameExistingProductNotPremitionFAil(){
        logout(sessionID1);
        String sessionID2 = login("alon1", "alon0601");
        int storeID = openStore(sessionID2, "newStore");
        int productID1 = addProduct(sessionID2,storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductName(sessionID2, storeID, productID1, "test11"));
    }

    @Test
    public void editCategoryExistingProductNotPremitionFAil(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductCategory(sessionID1, storeID, productID1, "test11"));
    }

    @Test
    public void editPriceExistingProductNotPremitionFAil(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductPrice(sessionID1, storeID, productID1, 1));
    }

    @Test
    public void editPriceExistingProductNegativeFAil(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductPrice(sessionID1, storeID, productID1, -5));
    }
}
