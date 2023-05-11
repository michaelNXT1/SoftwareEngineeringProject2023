package AccaptanceTests.GuestTest;



import AccaptanceTests.ServiceTests;
import BusinessLayer.ShoppingBag;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.ShoppingBagDTO;
import ServiceLayer.DTOs.ShoppingCartDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CartTest extends ServiceTests {

    /*
     * USE CASES 2.7.1-2.7.4
     *
     * */
    String sessionID1;
    @Before
    public void setUp(){
        super.setUp();
        register("alon1","alon0601");
        sessionID1 = login("alon1", "alon0601");
    }

    @After
    public void tearDown(){
        clearDB();

    }



    @Test
    public void testViewCartSuccessful(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",4.5,"milk",9,"10");
        int productID2 = addProduct(sessionID1, storeID,"test2",3.9,"milk",9,"10");
        addToCart(sessionID1, storeID,productID1,5);
        addToCart(sessionID1, storeID,productID2,5);
        boolean product1Exist = false;
        boolean product2Exist = false;
        ShoppingCartDTO shoppingCartDTO = viewCart(sessionID1);
        List<ShoppingBagDTO> bagList =  shoppingCartDTO.shoppingBags;
        for(ShoppingBagDTO bagDTO: bagList){
           for(ProductDTO productDTO: bagDTO.getProductList().keySet()){
               if(productDTO.getProductId() == productID1)
                   product1Exist = true;
               if(productDTO.getProductId() == productID2)
                   product2Exist = true;
           }
        }
        assertTrue(product1Exist && product2Exist);
    }


    @Test
    public void testEditAmountInCartSuccessful(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",4.5,"milk",9,"10");
        int productID2 = addProduct(sessionID1, storeID,"test2",3.9,"milk",9,"10");
        addToCart(sessionID1, storeID,productID1, 5);
        addToCart(sessionID1, storeID, productID2,2);
        assertTrue(updateAmount(sessionID1, storeID, productID1,1));
    }

    @Test
    public void testEditAmountInCartNonPositiveAmountFailure(){
        int storeID = openStore(sessionID1, "newStore");
        int productID1 = addProduct(sessionID1, storeID,"test",4.5,"milk",9,"10");
        assertFalse(updateAmount(sessionID1, storeID,productID1,-5));
    }

    @Test
    public void testEditAmountInCartProductNotInCartAmountFailure(){
        int storeID = openStore(sessionID1, "newStore");
        assertFalse(updateAmount(sessionID1, storeID,1,3));
    }

    //USE CASE 2.7.3
    @Test
    public void testDeleteItemInCartSuccessful(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",4.5,"milk",9,"10");
        addToCart(sessionID1,storeID,productID1, 5);
        assertTrue(deleteItemInCart(sessionID1,storeID, productID1));
    }

    //USE CASE 2.7.4
    @Test
    public void testDeleteItemNotExistFail(){
        int storeID = openStore(sessionID1,"newStore");
        int productID1 = addProduct(sessionID1,storeID,"test",4.5,"milk",9,"10");
        assertFalse(deleteItemInCart(sessionID1,storeID,productID1));
    }


}
