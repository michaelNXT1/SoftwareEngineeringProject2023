package AccaptanceTests;

import AccaptanceTests.bridge.Bridge;


import AccaptanceTests.bridge.Driver;
import junit.framework.TestCase;
import org.example.BusinessLayer.Member;
import org.junit.After;


public abstract class ServiceTests extends TestCase {

    Bridge bridge;


    public void setUp(){

        this.bridge = Driver.getBridge();

        this.bridge.setupSystem("alon","alon5931@gmail.com", "alon0601");

    }

    @After
    public void tearDown() {

    }

    public boolean openMarket(){
       return this.bridge.openMarket();
    }

    public String searchProductsByName(String productName) {
        return this.bridge.searchProductsByName(productName);
    }


    public String searchProductsByCategory(String productCategory) {
        return this.bridge.searchProductsByCategory(productCategory);
    }

    public String searchProductsBySubString(String productSubString) {
        return this.bridge.searchProductsBySubString(productSubString);
    }

    public String filterSearchResultsByCategory(String category){
        return this.bridge.filterSearchResultsByCategory(category);
    }

    public String filterSearchResultsByPrice(double minPrice, double maxPrice){
        return this.bridge.filterSearchResultsByPrice(minPrice, maxPrice);
    }

    public void clearDB(){
        this.bridge.clearDatabase(); // start tests with a clean database
    }


    public boolean login (String username, String email,String password){
        return this.bridge.login(username, email,password);
    }

    public boolean getStore(int storeID){
        return this.bridge.getStore(storeID);
    }

    public boolean getProduct(int productID, int storeID){
        return this.bridge.getProduct(productID, storeID);
    }

    public boolean register(String username, String email,String password){
        return this.bridge.register(username, email,password);
    }

    public boolean addToCart(int storeId, int productId, Integer amount){
        return this.bridge.addToCart(storeId, productId, amount);
    }

    public boolean updateAmount(int storeId, int productId, int amount){
        return bridge.updateAmount(storeId, productId, amount);
    }

    public boolean deleteItemInCart(int storeId, int productId){
        return bridge.deleteItemInCart(storeId, productId);
    }

//    public boolean clearCart(int sessionId){
//        return bridge.clearCart(sessionId);
//    }

    public boolean buyCart(int sessionId, String cardNumber, String cardMonth, String cardYear, String cardHolder,
                           String cardCcv, String cardId, String buyerName, String address, String city, String country, String zip){
        return bridge.buyCart(sessionId, cardNumber, cardMonth, cardYear, cardHolder, cardCcv, cardId, buyerName, address, city, country, zip);
    }

    public boolean logout(int sessionId){ return bridge.logout(); }

    public Integer openStore(String storeName){ return bridge.openStore(storeName); }

    public boolean addProductToStore(int sessionId, int productId, int storeId, int amount) { return bridge.addProductToStore(sessionId, productId, storeId, amount); }


    public Integer addProduct(int storeId, String productName, double price, String category, double rating, int quantity){return this.bridge.addProduct(storeId, productName, price, category, rating, quantity);}

    public boolean deleteProduct(int sessionId, int storeId, int productId) { return bridge.deleteProduct(sessionId, storeId, productId); }

    public boolean appointManager(int sessionId, int storeId, String userName) { return bridge.appointManager(sessionId, storeId, userName); }

//    public boolean appointOwner(int sessionId, int storeId, String userName) { return bridge.appointOwner(sessionId, storeId, userName); }

//    public boolean removeManager(int sessionId, int storeId, int userId) { return bridge.removeManager(sessionId, storeId, userId); }

//    public boolean removeOwner(int sessionId, int storeId, int userId) { return bridge.removeOwner(sessionId, storeId, userId); }

    public boolean editManagerOptions(int sessionId, int storeId, String userName, int option){ return bridge.editManagerOptions(sessionId, userName, storeId, option); }

//    public String searchProducts(int sessionId, String productName, String category, String[] keywords, int productRating, int storeRating, int priceFrom, int priceTo){
//        return this.bridge.searchProducts(sessionId, productName, category, keywords, productRating, storeRating, priceFrom, priceTo); }


    public String viewCart(){
        return this.bridge.showCart();
    }

    public String getAllInfo(String storeSub){
        return this.bridge.getStoresInform(storeSub);
    }

    public String viewPurchaseHistory(int sessionId, int storeID){
        return bridge.viewPurchaseHistory(storeID);
    }

//    public String searchUserHistory(int sessionId, int userId) { return this.bridge.searchUserHistory(sessionId, userId);}
//
//    public String getStoreHistory(int sessionId, int storeId) { return this.bridge.getStoreHistory(sessionId, storeId); }
//
//    public String viewShopHistory(int sessionId, int storeId){ return bridge.viewShopHistory(sessionId, storeId); }

//    public boolean setupSystem(String suppyConfig, String paymentConfig,String path) { return bridge.setupSystem(suppyConfig, paymentConfig,path); }

//    public int startSession() { return this.bridge.startSession(); }

//    public void addProductInfo(int sessionId, int id, String name, String category, double basePrice) { this.bridge.addProductInfo(sessionId, id, name, category, basePrice);}

}
