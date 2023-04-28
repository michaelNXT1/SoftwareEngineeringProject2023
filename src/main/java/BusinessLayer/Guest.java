package BusinessLayer;

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

    public void addProductToShoppingCart(Store s, int productId, int itemsAmount) throws Exception { //2.10
        shoppingCart.setProductQuantity(s, productId, itemsAmount);
    }

    public ShoppingCart displayShoppingCart() {  //2.11
        return shoppingCart;
    }

    public void changeProductQuantity(int productId, int newQuantity, Store s) throws Exception {    //2.12
        shoppingCart.setProductQuantity(s, productId, newQuantity);
    }

    public Purchase purchaseShoppingCart() throws Exception {    //2.14
        Purchase p = shoppingCart.purchaseShoppingCart();
        purchaseHistory.add(p);
        return p;
    }

    public void removeProductFromShoppingCart(Store s, int productId) throws Exception {
        shoppingCart.removeProduct(s, productId);
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

    public Store openStore(String storeName, int storeId) throws Exception {
        throw new Exception("Cannot perform action when not a member");
    }

    public String getUsername() throws Exception {
        throw new Exception("Cannot perform action when not a member");
    }

}
