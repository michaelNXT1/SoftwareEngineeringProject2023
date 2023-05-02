
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
        int products = searchProductsByName(sessionID1,"test");
        assertTrue(products>0);
    }

    @Test
    public void testSearchProductsByCategorySuccessful(){
        int products = searchProductsByCategory(sessionID1, "milk");
        assertTrue(products>0);
    }

    @Test
    public void testSearchProductsBySubStringSuccessful(){
        searchProductsByName(sessionID1,"test");
        int products = searchProductsBySubString(sessionID1 ,"te");
        assertTrue(products>0);
    }

    @Test
    public void testSearchProductsFilterCategorySuccessful(){
        searchProductsByCategory(sessionID1, "milk");
        assertTrue(filterSearchResultsByCategory(sessionID1, "milk")>0);
    }

    @Test
    public void testSearchProductsFilterPriceSuccessful(){
        assertNotNull(filterSearchResultsByPrice(sessionID1, 3,4));
    }
    @Test
    public void testSearchProductByNameFail(){
        int products = searchProductsByName(sessionID1 ,"missslk");
        assertTrue(products == 0);
    }

    @Test
    public void testSearchProductsByCategoryFail(){
        int products = searchProductsByCategory(sessionID1, "frozensss");
        assertTrue(products == 0);
    }

    @Test
    public void testSearchProductsBySubStringFail(){
        int products = searchProductsBySubString(sessionID1 ,"milksss");
        assertTrue(products == 0);
    }

    @Test
    public void testFilterByCategoryNoResults(){
        int prodcuts = filterSearchResultsByCategory(sessionID1 ,"Foodsss");
        assertTrue(prodcuts == 0);
    }

    @Test
    public void testFilterByPriceNoResults(){
        int prodcuts = filterSearchResultsByPrice(sessionID1, 1,3);
        assertTrue(prodcuts == 0);
    }


}