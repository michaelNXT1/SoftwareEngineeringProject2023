package UnitTests;

import BusinessLayer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {


    private ShoppingCart shoppingCart;
    private Market market;
    private Product product1;
    private Product product2;
    private Product product3;
    private Store store1;
    private Member member;
    private final String userName1 = "Idan111";
    private final String password1 = "ie9xzsz4321";
    private final String email1 = "idanlobel1@gmail.com";

    @BeforeEach
    void beforeEach() throws Exception {
        shoppingCart = new ShoppingCart();
        store1 = new Store(1, "Shufersal" , member);
        product1 = new Product(store1.getStoreId(),1,"wine", 3, "60.0", "alcohol");
        product2 = new Product(store1.getStoreId(),2, "cheese", 9.5, "milk","fads");
        product3 = new Product(store1.getStoreId(),3, "steak", 120.0, "meat","asd");
        market = new Market();
        market.signUp(userName1, password1);
        market.signUpSystemManager(userName1, password1);
        market.signUp("idan123",  "wswsad32");
        //shoppingCart.addProduct(product2, 2, store1);
    }

//    @Test
//    void addProduct() {
//        shoppingCart.addProduct(product1, 2, store1);
//        assertTrue(shoppingCart.shoppingBags.get(0).getProductList().containsKey(product1));
//    }

    @Test
    void changeProductQuantity() throws Exception {
        //shoppingCart.addProduct(product1, 2, store1);
        shoppingCart.setProductQuantity(store1,0,12);
        assertEquals(12 ,shoppingCart.shoppingBags.get(0).getProductList().get(product1));
    }

    @Test
    void removeProduct() throws Exception {
        shoppingCart.removeProduct(store1, 0);
        assertFalse(shoppingCart.shoppingBags.get(0).getProductList().containsKey(product1));
    }

    @Test
    void purchaseShoppingCart() throws Exception {
        Purchase purchase = shoppingCart.purchaseShoppingCart();
        assertTrue(purchase.getProductList().contains(product2));
    }

    //טסט שאין מספיק מוצרים במלאי
    //מערכת כספים לא עובד והמלאי לא משתנה ואין תשלום
    //שימוש בmocks
    //
}