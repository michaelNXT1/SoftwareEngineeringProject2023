package UnitTests;

import BusinessLayer.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {


    private static ShoppingCart shoppingCart;
    private static Market market;
    private static Product product1;
    private static Product product2;
    private static Product product3;
    private static Store store1;
    private static Member member;

    @BeforeAll
    static void beforeAll() throws Exception {
        shoppingCart = new ShoppingCart();
        market = new Market();
        market.signUpSystemManager("sysAdmin", "YoImTheBaws");
        market.signUp("idan123", "wswsad32");
        store1 = new Store(1, "Shufersal", member);
        product1 = store1.addProduct("wine", 60.0, "alcohol", 20, "aa");
        product2 = store1.addProduct("cheese", 9.5, "milk", 4, "bb");
        product3 = store1.addProduct("steak", 120.0, "meat", 10, "cc");
        //shoppingCart.addProduct(product2, 2, store1);
    }

//    @Test
//    void addProduct() {
//        shoppingCart.addProduct(product1, 2, store1);
//        assertTrue(shoppingCart.shoppingBags.get(0).getProductList().containsKey(product1));
//    }

    @Test
    void changeProductQuantity() throws Exception {
        shoppingCart.setProductQuantity(store1, 0, 12);
        assertEquals(12, shoppingCart.shoppingBags.get(0).getProductList().get(product1.getProductId()));
    }

    @Test
    void removeProduct() throws Exception {
        shoppingCart.setProductQuantity(store1, 0, 12);
        shoppingCart.removeProduct(store1, 0);
        //TODO: purchase doesn't hold Product, but PurchaseProduct, need to fix
        assertFalse(shoppingCart.shoppingBags.get(0).getProductList().containsKey(product1.getProductId()));
    }

    @Test
    void purchaseShoppingCart() throws Exception {
        shoppingCart.setProductQuantity(store1, product2.getProductId(), 3);
        Purchase purchase = shoppingCart.purchaseShoppingCart();
        //TODO: purchase doesn't hold Product, but PurchaseProduct, need to fix
        assertTrue(purchase.getProductList().stream().anyMatch(pp -> pp.getProductId() == product2.getProductId()));
    }

    //טסט שאין מספיק מוצרים במלאי
    //מערכת כספים לא עובד והמלאי לא משתנה ואין תשלום
    //שימוש בmocks
    //
}