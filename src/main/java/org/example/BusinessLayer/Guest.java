package org.example.BusinessLayer;

import java.util.List;

public class Guest {

    private ShoppingCart shoppingCart;
    private List<Product> searchResults;
    void addProductToShoppingCart(Store s, Product p, int itemsAmount){
        shoppingCart.setProductQuantity(s, p, itemsAmount);
    }
    public ShoppingCart getShoppingCart() {
        return null;
    }
    public void changeProductAmount(int productID, int productAmount){

    }
    public void removeProductFromShoppingCart(Store s, Product p){
        shoppingCart.removeProduct(s, p);
    }
    public void emptyShoppingCart(){}

    public List<Product> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Product> searchResults) {
        this.searchResults = searchResults;
    }

    public void purchaseShoppingCart() {
    }
}
