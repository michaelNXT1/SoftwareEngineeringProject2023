package org.example.BusinessLayer;

import java.util.List;

public class Guest {

    private ShoppingCart shoppingCart;
    void addProductToShoppingCart(int storeID, int productID, int itemsAmount){

    }
    public void changeProductAmount(int productID, int productAmount){

    }
    public void removeProductFromShoppingCart(int productID){}
    public Purchase purchaseShoppingCart() {    //2.14
        return shoppingCart.purchaseShoppingCart();
    }


}
