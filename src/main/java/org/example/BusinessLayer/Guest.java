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
    public void removeProductFromShoppingCart(Product p, Store s){  //2.13
        shoppingCart.removeProduct(p, s);
    }
    public void emptyShoppingCart(){}   //2.14


}
