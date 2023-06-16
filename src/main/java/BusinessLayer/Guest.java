package BusinessLayer;

import DAOs.ProductDAO;
import DAOs.PurchaseDAO;
import DAOs.ShoppingBagDAO;
import DAOs.ShoppingCartDAO;
import Repositories.IProductRepository;
import Repositories.IShoppingBagRepository;
import Repositories.IShoppingCartRepo;
import jakarta.persistence.*;

import java.util.ArrayList;
import Repositories.IPurchaseRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.atmosphere.annotation.AnnotationUtil.logger;

public class Guest {

    private static Long IdCounter = 0L;
    private Long id;

    protected ShoppingCart shoppingCart;

    private String searchKeyword;

    private List<Product> searchResults;
    private IPurchaseRepository purchaseHistory;
    protected IShoppingCartRepo shoppingCartRepo = new ShoppingCartDAO();
    private PaymentDetails paymentDetails;
    private SupplyDetails supplyDetails;

    public void setId(Long id) {
        this.id = id;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public void setSupplyDetails(SupplyDetails supplyDetails) {
        this.supplyDetails = supplyDetails;
    }

    public Guest() {
        this.id = IdCounter;
        IdCounter++;
        searchResults = new ArrayList<>();
        purchaseHistory = new PurchaseDAO();
        paymentDetails = null;
        supplyDetails = null;
    }

    public void addShoppingCart(){
        shoppingCart = new ShoppingCart(null);
    }

    public void addProductToShoppingCart(Store s, int productId, int itemsAmount) throws Exception { //2.10
        shoppingCart.setProductQuantity(s, productId, itemsAmount);
    }

    public ShoppingCart displayShoppingCart(Long id) {  //2.11
        return shoppingCart;
    }
    public IPurchaseRepository getPurchaseHistory(){
        return this.purchaseHistory;
    }
    public void setPurchaseHistory(IPurchaseRepository purchaseHistory){
        this.purchaseHistory = purchaseHistory;
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
        return searchResults;
    }

    public void setSearchResults(List<Product> searchResults) {
        this.searchResults = searchResults;
    }

    public List<Product> filterSearchResultsByCategory(String category) {
        List<Product> allProducts = searchResults;
        return allProducts.stream().filter(p -> p.getCategory().equals(category)).collect(Collectors.toList());
    }

    public List<Product> filterSearchResultsByPrice(double minPrice, double maxPrice) {
        List<Product> allProducts = searchResults;
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

    public static Long getIdCounter() {
        return IdCounter;
    }

    public static void setIdCounter(Long idCounter) {
        IdCounter = idCounter;
    }

    public IShoppingCartRepo getShoppingCartRepo() {
        return shoppingCartRepo;
    }

    public void setShoppingCartRepo(IShoppingCartRepo shoppingCartRepo) {
        this.shoppingCartRepo = shoppingCartRepo;
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
