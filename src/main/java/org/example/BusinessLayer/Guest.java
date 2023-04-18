package org.example.BusinessLayer;

import java.util.List;

public class Guest {

    private ShoppingCart shoppingCart;
    private List<Product> searchResults;
    void addProductToShoppingCart(Store s, Product p, int itemsAmount){ //2.10
        shoppingCart.setProductQuantity(s, p, itemsAmount);
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
    public void removeProductFromShoppingCart(Store s, Product p){
        shoppingCart.removeProduct(s, p);
    }

    public List<Product> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Product> searchResults) {
        this.searchResults = searchResults;
    }
}
