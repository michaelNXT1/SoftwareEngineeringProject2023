package AccaptanceTests.GuestTest;

import AccaptanceTests.ServiceTests;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.ShoppingBagDTO;
import ServiceLayer.DTOs.ShoppingCartDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class PurchaseTests extends ServiceTests {
    /*
     * USE CASES 2.8.1-2.8.4
     *
     * */
    int storeID2;
    int productID2;
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1", "alon0601");
        register("alon12", "alon0601");
        sessionID1 = login("alon1","alon0601");
        storeID2 = openStore(sessionID1, "newStore3");
        productID2 = addProduct(sessionID1, storeID2,"test2",3.9,"milk",9,"1");
        addPaymentMethod(sessionID1,"124","12","2026","540");
        addSupplyDetails(sessionID1,"abc","abc","abc","abc","abc");
        addToCart(sessionID1, storeID2, productID2, 5);
    }

    @After
    public void tearDown(){
        clearDB();
    }



    @Test
    public void testPurchaseSuccessful(){
        boolean product1Exist = false;
        ShoppingCartDTO shoppingCartDTO = viewCart(sessionID1);
        List<ShoppingBagDTO> bagList =  shoppingCartDTO.shoppingBags;
        for(ShoppingBagDTO bagDTO: bagList){
            for(ProductDTO productDTO: bagDTO.getProductList().keySet()){
                if(productDTO.getProductId() == productID2)
                    product1Exist = true;
            }
        }
        assertTrue(product1Exist && buyCart(sessionID1)!=null);
    }
    @Test
    public void testNotEngItemsQuantityFail(){
        int productID1 = addProduct(sessionID1, storeID2,"test4",3.9,"milk",9,"1");
        logout(sessionID1);
        String sessionId2 = login("alon12", "alon0601");
        addToCart(sessionId2, storeID2, productID2, 5);
        buyCart(sessionId2);
        logout(sessionId2);
        login("alon1", "alon0601");
        assertNull(buyCart(sessionID1));
    }
    @Test
    public void testBuyingPolicyFail(){
        addMinQuantityPolicy(sessionID1,storeID2,productID2,3,false);
        addToCart(sessionID1, storeID2, productID2, 2);
        assertNull(buyCart(sessionID1));
    }

    @Test
    public void testNothingInCartFail(){
        deleteItemInCart(sessionID1,storeID2,productID2);
        assertNull(buyCart(sessionID1));
    }

}
