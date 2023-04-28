
package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchProductTests extends ServiceTests {
    /*
     * USE CASES 2.5
     *
     * */
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon0601");
        sessionID1 = login("alon1", "alon0601");
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",4.5,"milk",9,"1");
        int productID2 = addProduct(sessionID1 ,storeID,"test2",3.9,"milk",9,"1");
        logout("1");

    }

    @After
    public void tearDown(){
        clearDB();
    }

    @Test
    public void testSearchProductByNameSuccessful(){
        String products = searchProductsByName(sessionID1,"test");
        assertNotNull(products);
    }

    @Test
    public void testSearchProductsByCategorySuccessful(){
        String products = searchProductsByCategory(sessionID1, "milk");
        assertNotNull(products);
    }

    @Test
    public void testSearchProductsBySubStringSuccessful(){
        String products = searchProductsBySubString(sessionID1 ,"fo");
        assertNotNull(products);
    }

    @Test
    public void testSearchProductsFilterCategorySuccessful(){
        assertNotNull(filterSearchResultsByCategory(sessionID1, "milk"));
    }

    @Test
    public void testSearchProductsFilterPriceSuccessful(){
        assertNotNull(filterSearchResultsByPrice(sessionID1, 3,4));
    }
    @Test
    public void testSearchProductByNameFail(){
        String products = searchProductsByName(sessionID1 ,"missslk");
        assertNull(products);
    }

    @Test
    public void testSearchProductsByCategoryFail(){
        String products = searchProductsByCategory(sessionID1, "frozensss");
        assertNull(products);
    }

    @Test
    public void testSearchProductsBySubStringFail(){
        String products = searchProductsBySubString(sessionID1 ,"milksss");
        assertNull(products);
    }

    @Test
    public void testFilterByCategoryNoResults(){
        String prodcuts = filterSearchResultsByCategory(sessionID1 ,"Foodsss");
        assertNull(prodcuts);
    }

    @Test
    public void testFilterByPriceNoResults(){
        String prodcuts = filterSearchResultsByPrice(sessionID1, 1,3);
        assertNull(prodcuts);
    }


}