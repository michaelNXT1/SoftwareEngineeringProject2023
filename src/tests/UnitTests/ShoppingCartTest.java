package UnitTests;

import org.example.BusinessLayer.*;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    void beforeAll() throws Exception {
        shoppingCart = new ShoppingCart();
        product1 = new Product(1, "wine", 60.0, "alcohol");
        product2 = new Product(2, "cheese", 9.5, "milk");
        product3 = new Product(3, "steak", 120.0, "meat");
        market = new Market();
        market.signUp("idan123", "idanlobel2@gmail.com", "wswsad32");
        store1 = new Store(1, "Shufersal" , member);
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
        //TODO: purchase doesn't hold Product, but PurchaseProduct, need to fix
        assertFalse(shoppingCart.shoppingBags.get(0).getProductList().containsKey(product1));
    }

    @Test
    void purchaseShoppingCart() throws Exception {
        Purchase purchase = shoppingCart.purchaseShoppingCart();
        //TODO: purchase doesn't hold Product, but PurchaseProduct, need to fix
        assertTrue(purchase.getProductList().contains(product2));
    }

    //טסט שאין מספיק מוצרים במלאי
    //מערכת כספים לא עובד והמלאי לא משתנה ואין תשלום
    //שימוש בmocks
    //
}