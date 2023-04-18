package org.example.BusinessLayer;

import java.util.List;

public class Guest {

    private ShoppingCart shoppingCart;
    void addProductToShoppingCart(int storeID, int productID, int itemsAmount){

    }
    public void changeProductQuantity(Product p, int newQuantity, Store s) {
        shoppingCart.changeProductQuantity(p, newQuantity, s);
    }
    public void emptyShoppingCart() {   //2.14
        shoppingCart = new ShoppingCart();
    }
    public void removeProductFromShoppingCart(Product p, Store s){  //2.13
        shoppingCart.removeProduct(p, s);
    }


}
