package BusinessLayer;

import DAOs.ProductDAO;
import DAOs.PurchaseDAO;
import Repositories.IProductRepository;
import jakarta.persistence.*;

import java.util.ArrayList;
import Repositories.IPurchaseRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import static org.atmosphere.annotation.AnnotationUtil.logger;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;
    @Transient
    private String searchKeyword;
    @OneToOne(cascade = CascadeType.ALL)
    private IProductRepository searchResults;
    @OneToMany(cascade = CascadeType.ALL)
    private IPurchaseRepository purchaseHistory;
    @OneToOne(cascade = CascadeType.ALL)
    private PaymentDetails paymentDetails;
    @OneToOne(cascade = CascadeType.ALL)
    private SupplyDetails supplyDetails;

    public Guest() {
        this.id = 0L; // Initializing with a default value
        shoppingCart = new ShoppingCart();
        searchResults = new ProductDAO();
        purchaseHistory = new PurchaseDAO();
        paymentDetails = null;
        supplyDetails = null;
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
            logger.error("no payment details exist");
            throw new Exception("no payment details exist");
        }
        if (supplyDetails == null) {
            logger.error("no supply details exist");
            throw new Exception("no supply details exist");
        }
        Purchase p = shoppingCart.purchaseShoppingCart();
        purchaseHistory.savePurchase(p);
        return p;
    }

    public void removeProductFromShoppingCart(Store s, int productId) throws Exception {
        shoppingCart.removeProduct(s, productId);
    }

    public List<Product> getSearchResults() {
        return searchResults.getAllProducts();
    }

    public void setSearchResults(IProductRepository searchResults) {
        this.searchResults = searchResults;
    }

    public List<Product> filterSearchResultsByCategory(String category) {
        List<Product> allProducts = searchResults.getAllProducts();
        return allProducts.stream().filter(p -> p.getCategory().equals(category)).collect(Collectors.toList());
    }

    public List<Product> filterSearchResultsByPrice(double minPrice, double maxPrice) {
        List<Product> allProducts = searchResults.getAllProducts();
        return allProducts.stream().filter(p -> minPrice <= p.getPrice() && p.getPrice() <= maxPrice).collect(Collectors.toList());
    }


    public void addPaymentMethod(String cardNumber, String month, String year, String cvv, String holder, String cardId) {
        paymentDetails = new PaymentDetails(cardNumber, month, year, cvv, holder, cardId);
    }
    public void addSupplyDetails(String name , String address, String city, String country, String zip) {
        supplyDetails = new SupplyDetails(name, address, city, country, zip);
    }

    public Store openStore(String storeName, int storeId) throws Exception {
        logger.error("Cannot perform action when not a member");
        throw new Exception("Cannot perform action when not a member");
    }

    public boolean isLoggedIn(){
        return false;
    }

    public String getUsername() throws Exception {
        logger.error("Cannot perform action when not a member");
        throw new Exception("Cannot perform action when not a member");
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void revertPurchase(Purchase purchase) {
        purchaseHistory.removePurchase(purchase);
    }

    public Collection<Purchase> getPurchaseHistory() {
        return purchaseHistory.getAllPurchases();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public SupplyDetails getSupplyDetails() {
        return supplyDetails;
    }

    public Long getId() {
        return id;
    }
}
