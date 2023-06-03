package UnitTests;

import BusinessLayer.*;
import ServiceLayer.DTOs.PurchaseDTO;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest extends TestCase {


    private ShoppingCart shoppingCart;
    private Market market;
    private Product product1;
    private Product product2;
    private Product product3;
    int storeId1;
    String sessionId1;
    private Member member;
    private final String userName1 = "Idan111";
    private final String password1 = "ie9xzsz4321";
    private final String email1 = "idanlobel1@gmail.com";

    @BeforeEach
    void beforeEach() throws Exception {
        shoppingCart = new ShoppingCart();
        product1 = new Product(storeId1,1,"wine", 3, "60.0", "alcohol");
        product2 = new Product(storeId1,2, "cheese", 9.5, "milk","fads");
        product3 = new Product(storeId1,3, "steak", 120.0, "meat","asd");
        market = new Market(null);
        market.signUp(userName1, password1);
        market.signUpSystemManager(userName1, password1);
        market.signUp("idan123",  "wswsad32");
        sessionId1 =market.login("idan123",  "wswsad32",null);
        storeId1 = market.openStore(sessionId1, "storeName");
        //shoppingCart.addProduct(product2, 2, store1);
    }

//    @Test
//    void addProduct() {
//        shoppingCart.addProduct(product1, 2, store1);
//        assertTrue(shoppingCart.shoppingBags.get(0).getProductList().containsKey(product1));
//    }

    @Test
    void changeProductQuantity() throws Exception {
        market.addProduct(sessionId1, storeId1, product1.getProductName(),20.0,"cat",50,"wow");
        market.changeProductQuantity(sessionId1, storeId1, product1.getProductId(),12);
        assertEquals(product1.getAmount(),12);
    }

    @Test
    void removeProduct() throws Exception {
        market.removeProductFromCart(sessionId1, storeId1, product1.getProductId());
        assertFalse(shoppingCart.shoppingBags.getShoppingBagById(0).getProductList().containsKey(product1.getProductId()));
    }

    @Test
    void purchaseShoppingCart() throws Exception {
        Purchase purchase = shoppingCart.purchaseShoppingCart();
        assertTrue(purchase.getProductList().contains(product2));
    }
    @Test
    void purchaseShoppingCartWhenSupplySystemFailed() throws Exception {
        SupplySystemProxy ss = new SupplySystemProxy();
        ss.succeedSupply = false;
        market.setSupplySystem(ss);
        market.addSupplyDetails(sessionId1, "shoham", "alexnder", "beer sheva", "Israel","806459");
        market.addPaymentMethod(sessionId1, "123456789", "April","2023","489","shoham","508479063");

        Exception exception = assertThrows(Exception.class, () -> market.purchaseShoppingCart(sessionId1));
        assertEquals("Purchase failed, supply system hasn't managed to charge", exception.getMessage());
    }
    @Test
    void purchaseShoppingCartWhenPaymentSystemFailed() throws Exception {
        PaymentSystemProxy ps = new PaymentSystemProxy();
        ps.succeedPayment = false;
        market.setPaymentSystem(ps);
        market.addSupplyDetails(sessionId1, "shoham", "alexnder", "beer sheva", "Israel","806459");
        market.addPaymentMethod(sessionId1, "123456789", "April","2023","489","shoham","508479063");

        Exception exception = assertThrows(Exception.class, () -> market.purchaseShoppingCart(sessionId1));
        assertEquals("Purchase failed, payment system hasn't managed to charge", exception.getMessage());
    }

    //טסט שאין מספיק מוצרים במלאי
    //מערכת כספים לא עובד והמלאי לא משתנה ואין תשלום
    //שימוש בmocks
    //
}