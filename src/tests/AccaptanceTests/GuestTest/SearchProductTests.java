
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
        int sessionId = 1;
        int storeID = 1;


        login("hanamaru", "abc@gmail.com","12345");
        openStore("newStore");
        addProductToStore(sessionId, 1, storeID, 5);
        addProductToStore(sessionId, 2, storeID, 5);
        logout(sessionId);

        login("jjjj", "abcג@gmail.com","12345");
        openStore("jjj'sStore");
        addProductToStore(sessionId, 1, storeID, 10);
        logout(sessionId);
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