package BusinessLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.atmosphere.annotation.AnnotationUtil.logger;

public class Guest {

    private ShoppingCart shoppingCart;
    private List<Product> searchResults;
    private List<Purchase> purchaseHistory;
    private PaymentDetails paymentDetails;

    public Guest() {
        shoppingCart = new ShoppingCart();
        searchResults = new ArrayList<>();
        purchaseHistory = new ArrayList<>();
        paymentDetails = null;
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
        if (paymentDetails == null) {
            logger.error(String.format("no payment details exist"));
            throw new Exception("no payment details exist");
        }
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

    public void addPaymentMethod(String cardNumber, String month, String year, String cvv) {
        paymentDetails = new PaymentDetails(cardNumber, month, year, cvv);
    }

    public Store openStore(String storeName, int storeId) throws Exception {
        logger.error(String.format("Cannot perform action when not a member"));
        throw new Exception("Cannot perform action when not a member");
    }

    public String getUsername() throws Exception {
        logger.error(String.format("Cannot perform action when not a member"));
        throw new Exception("Cannot perform action when not a member");
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void revertPurchase(Purchase purchase) {
        purchaseHistory.remove(purchase);
    }

    public Collection<Purchase> getPpurchaseHistory() {
        return purchaseHistory;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
