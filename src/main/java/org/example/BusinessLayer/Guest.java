package org.example.BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Guest {

    private ShoppingCart shoppingCart;
    private List<Product> searchResults;
    private List<Purchase> purchaseHistory;

    public Guest() {
        shoppingCart = new ShoppingCart();
        searchResults = new ArrayList<>();
        purchaseHistory = new ArrayList<>();
    }

    void addProductToShoppingCart(Store s, Product p, int itemsAmount) { //2.10
        shoppingCart.setProductQuantity(s, p, itemsAmount);
    }

    public ShoppingCart displayShoppingCart() {  //2.11
        return shoppingCart;
    }

    public void changeProductQuantity(Product p, int newQuantity, Store s) {    //2.12
        shoppingCart.setProductQuantity(s, p, newQuantity);
    }

    public Purchase purchaseShoppingCart() {    //2.14
        Purchase p = shoppingCart.purchaseShoppingCart();
        purchaseHistory.add(p);
        return p;
    }

    public void removeProductFromShoppingCart(Store s, Product p) {
        shoppingCart.removeProduct(s, p);
    }

    public List<Product> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Product> searchResults) {
        this.searchResults = searchResults;
    }

    public List<Product> filterSearchResultsByCategory(String category) {
        return searchResults.stream().filter(p -> p.getCategory().equals(category)).collect(Collectors.toList());
    }

    public List<Product> filterSearchResultsByPrice(double minPrice, double maxPrice) {
        return searchResults.stream().filter(p -> minPrice <= p.getPrice() && p.getPrice() <= maxPrice).collect(Collectors.toList());
    }
}
