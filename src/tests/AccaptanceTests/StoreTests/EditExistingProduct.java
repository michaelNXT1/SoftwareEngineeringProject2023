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
    public void testEditNameExistingProductSuccess(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductName(sessionID1, storeID, productID1, "test11"));
    }

    @Test
    public void testEditCategoryExistingProductSuccess(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductCategory(sessionID1, storeID, productID1, "test11"));
    }

    @Test
    public void testEditPriceExistingProductSuccess(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1 ,storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductPrice(sessionID1, storeID, productID1, 1));
    }

    @Test
    public void testEditNameExistingProductFail(){
        int storeID = openStore(sessionID1, "newStore");
        assertFalse(editProductName(sessionID1, storeID, 1, "test11"));
    }

    @Test
    public void testEditCategoryExistingProductFail(){
        int storeID = openStore(sessionID1, "newStore");
        assertFalse(editProductCategory(sessionID1, storeID, 1, "test11"));
    }

    @Test
    public void testEditPriceExistingProductFail(){
        int storeID = openStore(sessionID1, "newStore");
        assertFalse(editProductPrice(sessionID1, storeID, 1, 1));
    }

    @Test
    public void testEditNameExistingProductNotPremitionFAil(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",3.9,"milk",9,"10");
        logout(sessionID1);
        String sessionID2 = login("alon12", "alon0601");
        assertFalse(editProductName(sessionID2, storeID, productID1, "test11"));
    }

    @Test
    public void testEditCategoryExistingProductNotPremitionFAil(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductCategory(sessionID1, storeID, productID1, "test11"));
    }

    @Test
    public void testEditPriceExistingProductNotPremitionFAil(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductPrice(sessionID1, storeID, productID1, 1));
    }

    @Test
    public void testEditPriceExistingProductNegativeFAil(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",3.9,"milk",9,"10");
        assertFalse(editProductPrice(sessionID1, storeID, productID1, -5));
    }
}
