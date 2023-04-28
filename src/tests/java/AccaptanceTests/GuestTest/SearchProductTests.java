
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
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon59311@gmail.com", "alon0601");
        login("alon1","alon59311@gmail.com", "alon0601");
        int storeID = openStore("newStore");
        int productID1 = addProduct(storeID,"test",4.5,"milk",9,"1");
        int productID2 = addProduct(storeID,"test2",3.9,"milk",9,"1");
        logout("1");

    }

    @After
    public void tearDown(){
        clearDB();
    }

    @Test
    public void testSearchProductByNameSuccessful(){
        String products = searchProductsByName("test");
        assertNotNull(products);
    }

    @Test
    public void testSearchProductsByCategorySuccessful(){
        String products = searchProductsByCategory("milk");
        assertNotNull(products);
    }

    @Test
    public void testSearchProductsBySubStringSuccessful(){
        String products = searchProductsBySubString("fo");
        assertNotNull(products);
    }

    @Test
    public void testSearchProductsFilterCategorySuccessful(){
        assertNotNull(filterSearchResultsByCategory("milk"));
    }

    @Test
    public void testSearchProductsFilterPriceSuccessful(){
        assertNotNull(filterSearchResultsByPrice(3,4));
    }
    @Test
    public void testSearchProductByNameFail(){
        String products = searchProductsByName("missslk");
        assertNull(products);
    }

    @Test
    public void testSearchProductsByCategoryFail(){
        String products = searchProductsByCategory("frozensss");
        assertNull(products);
    }

    @Test
    public void testSearchProductsBySubStringFail(){
        String products = searchProductsBySubString("milksss");
        assertNull(products);
    }

    @Test
    public void testFilterByCategoryNoResults(){
        String prodcuts = filterSearchResultsByCategory("Foodsss");
        assertNull(prodcuts);
    }

    @Test
    public void testFilterByPriceNoResults(){
        String prodcuts = filterSearchResultsByPrice(1,3);
        assertNull(prodcuts);
    }


}