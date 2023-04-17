package org.example.BusinessLayer;

import java.util.List;

public class Guest {

    private ShoppingCart shoppingCart;
    void addProductToShoppingCart(int storeID, int productID, int itemsAmount){

    }
    public List<Product> getShoppingCart() {
        return null;
    }
    public void changeProductAmount(int productID, int productAmount){

    }
    public void removeProductFromShoppingCart(int productID){}
    public void emptyShoppingCart() {
        shoppingCart = new ShoppingCart();
    }

}
