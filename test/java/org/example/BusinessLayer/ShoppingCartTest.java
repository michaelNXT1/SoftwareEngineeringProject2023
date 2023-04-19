package org.example.BusinessLayer;

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
        product1 = new Product(1, "wine", 60.0, "alcohol", 4.5, 10);
        product2 = new Product(2, "cheese", 9.5, "milk", 3.5, 20);
        product3 = new Product(3, "steak", 120.0, "meat", 2.5, 30);
        market = new Market();
        market.signUp("idan123", "idanlobel2@gmail.com", "wswsad32");
        member = market.getMemberFromUser("idan123");
        store1 = new Store(1, "Shufersal" , member);
        shoppingCart.addProduct(product2, 2, store1);
    }

    @Test
    void addProduct() {
        shoppingCart.addProduct(product1, 2, store1);
        assertTrue(shoppingCart.shoppingBags.get(0).getProductList().containsKey(product1));
    }

    @Test
    void changeProductQuantity() {
        shoppingCart.addProduct(product1, 2, store1);
        shoppingCart.changeProductQuantity(product1, 3, store1);
        assertEquals(3 ,shoppingCart.shoppingBags.get(0).getProductList().get(product1));
    }

    @Test
    void removeProduct() {
        shoppingCart.removeProduct(product1,  store1);
        assertFalse(shoppingCart.shoppingBags.get(0).getProductList().containsKey(product1));
    }

    @Test
    void purchaseShoppingCart() {
        Purchase purchase = shoppingCart.purchaseShoppingCart();
        assertTrue(purchase.getProductList().contains(product2));
    }
}