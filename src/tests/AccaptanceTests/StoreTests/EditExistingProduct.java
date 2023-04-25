package AccaptanceTests.StoreTests;

import AccaptanceTests.ServiceTests;
import org.junit.Before;
import org.junit.Test;

public class EditExistingProduct extends ServiceTests {
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        register("alon12","alon593112@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");

    }

    @Test
    public void editNameExistingProductSuccess(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductName(storeID, productID1, "test11"));
    }

    @Test
    public void editCategoryExistingProductSuccess(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductCategory(storeID, productID1, "test11"));
    }

    @Test
    public void editPriceExistingProductSuccess(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductPrice(storeID, productID1, 1));
    }

    @Test
    public void editNameExistingProductFail(){
        int storeID = openStore("newStore");
        assertTrue(editProductName(storeID, 1, "test11"));
    }

    @Test
    public void editCategoryExistingProductFail(){
        int storeID = openStore("newStore");
        assertTrue(editProductCategory(storeID, 1, "test11"));
    }

    @Test
    public void editPriceExistingProductFail(){
        int storeID = openStore("newStore");
        assertTrue(editProductPrice(storeID, 1, 1));
    }

    @Test
    public void editNameExistingProductNotPremitionFAil(){
        logout(1);
        login("alon1","alon59311@gmail.com", "alon0601");
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductName(storeID, productID1, "test11"));
    }

    @Test
    public void editCategoryExistingProductNotPremitionFAil(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductCategory(storeID, productID1, "test11"));
    }

    @Test
    public void editPriceExistingProductNotPremitionFAil(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductPrice(storeID, productID1, 1));
    }

    @Test
    public void editPriceExistingProductNegativeFAil(){
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",3.9,"milk",9,"10");
        assertTrue(editProductPrice(storeID, productID1, -5));
    }
}
