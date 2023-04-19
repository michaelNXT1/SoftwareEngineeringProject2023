
package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;

import org.example.BusinessLayer.Member;
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
        addProductToStore(1, 1, storeID, 5);
        addProductToStore(1, 2, storeID, 5);
        logout(1);

        login("jjjj", "abcג@gmail.com","12345");
        openStore("jjj'sStore");
        addProductToStore(1, 1, storeID, 10);
        logout(1);
    }

    @After
    public void tearDown(){
        clearDB();
    }

    @Test
    public void testSearchProductByNameSuccessful(){
        String products = searchProductsByName("milk");
        assertNotNull(products);
    }

    @Test
    public void testSearchProductsByCategorySuccessful(){
        String products = searchProductsByCategory("frozen");
        assertNotNull(products);
    }

    @Test
    public void testSearchProductsBySubStringSuccessful(){
        String products = searchProductsBySubString("milk");
        assertNotNull(products);
    }

    @Test
    public void testSearchProductsFilterSuccessful(){
        assertNotNull(filterSearchResultsByCategory("Food"));
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
    public void testSearchProductsNoResults(){
        String prodcuts = filterSearchResultsByCategory("Foodsss");
        assertNull(prodcuts);

    }


}