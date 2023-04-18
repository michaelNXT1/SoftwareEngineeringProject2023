package org.example.BusinessLayer;

import java.util.List;

public class Guest {

    private ShoppingCart shoppingCart;
    void addProductToShoppingCart(int storeID, int productID, int itemsAmount){

    }
    public ShoppingCart DisplayShoppingCart(){  //2.11
        return shoppingCart;
    }
    public void changeProductQuantity(Product p, int newQuantity, Store s) {    //2.12
        shoppingCart.changeProductQuantity(p, newQuantity, s);
    }

    public Purchase purchaseShoppingCart() {    //2.14
        return shoppingCart.purchaseShoppingCart();
    }
    public void removeProductFromShoppingCart(Product p, Store s){  //2.13
        shoppingCart.removeProduct(p, s);
    }


}
