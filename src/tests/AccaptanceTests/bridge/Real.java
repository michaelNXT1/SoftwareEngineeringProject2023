package AccaptanceTests.bridge;


import org.example.ServiceLayer.MarketManager;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class Real implements Bridge {

    MarketManager manager = new MarketManager();
//    public boolean setupSystem(String supplyConfig, String paymentConfig,String path) {
//        SessionHandler dc = new SessionHandler();
//        return dc.setup(supplyConfig, paymentConfig,path).getResultCode() == ResultCode.SUCCESS;
//    }

    public boolean login(String username,String email,String password) {
        return manager.login(username, email, password).getError_occurred();
    }

    public boolean register(String username,String email,String password) {
        return manager.signUp(username, email,password).getError_occurred();
    }

    public String getStoresInform(String storeSubString) {
        return manager.getStores(storeSubString).value.toString();
    }

    public String searchProductsByName(String productName) {
        return manager.searchProductsByName(productName).toString();
    }

    public String searchProductsByCategory(String productCategory) {
        return manager.searchProductsByCategory(productCategory).toString();
    }

    public String searchProductsBySubString(String productSubString) {
        return manager.searchProductsBySubString(productSubString).toString();
    }


    public String filter(String filterParams){
        return manager.filter(filterParams).toString();
    }


    public boolean addToCart(int storeId, int productId, int amount) {
        return manager.addProductToCart(storeId, productId, amount).getError_occurred();
    }


    public String showCart(int sessionID){
        return manager.showCart(sessionID).value.toString();
    }


    public boolean updateAmount(int storeId,int productId, int amount) {
        return manager.editProductInCart(storeId, productId, amount).getError_occurred();
    }

    public boolean deleteItemInCart(int storeId, int productId) {
        return manager.removeProductInCart(storeId, productId).getError_occurred();
    }

    public boolean clearCart(int sessionId) {
        return manager.clearCart(sessionId).getError_occurred();
    }

    public boolean buyCart(int sessionId, String cardNumber, String cardMonth, String cardYear, String cardHolder,
                           String cardCcv, String cardId, String buyerName, String address, String city, String country, String zip) {
        if (!manager.requestPurchase(sessionId).ggetError_occurred()) {
            return manager.confirmPurchase(sessionId, cardNumber, cardMonth, cardYear, cardHolder, cardCcv,
                    cardId, buyerName, address, city, country, zip).getError_occurred();
        }

        return false;
    }


    public boolean logout(int sessionId){
        return manager.logout(sessionId).getError_occurred();

    }

    public boolean openStore(int sessionId,String storeName, String newProducts) {
        return manager.openStore(sessionId, storeName, newProducts).getError_occurred();
    }

    public String viewPurchaseHistory(int sessionId, int storeID){
        return manager.getHistory(sessionId, storeID).value.toString();
    }


    public boolean addProductToStore(int sessionId, int productId, int storeId, int amount) {
        return manager.addProductToStore(productId,storeId,amount).getError_occurred();
    }

    public boolean editProduct(int sessionId, int storeId, int productId, String productInfo) {
        return manager.editProductToStore(storeId, productId, productInfo).getError_occurred();
    }

    public boolean deleteProduct(int sessionId, int storeId, int productId) {
        return manager.deleteProductFromStore(storeId, productId).getError_occurred();
    }

    public boolean changeBuyingPolicy(int sessionId, int storeId, String newPolicy){
            return manager.changeBuyingPolicy(storeId, newPolicy).getError_occurred();
    }


    public boolean changeSalesPolicy(int sessionId, int storeId, String newPolicy){
        return manager.changeSalesPolicy(storeId, newPolicy).getError_occurred();
    }

    public boolean editStorePurchaseType(int sessionId, int storeId, String newType){
        return manager.editStorePurchaseType(sessionId, storeId, newType).getError_occurred();
    }

    public boolean editStoreSalesType(int sessionId, int storeId, String newType){
        return manager.editStoreSalesType(sessionId, storeId, newType).getError_occurred();
    }


    public boolean appointManager(int sessionId, int storeId, int userId) {
        return manager.addStoreManager(storeId, userId).getError_occurred();
    }

    public boolean removeManager(int sessionId, int storeId, int userId) {
        return manager.deleteManager(storeId, userId).getError_occurred();
    }

    public boolean editManagerOptions(int sessionId, int storeId, int userId, String option){
        return manager.editManageOptions(storeId, userId, option).getError_occurred();
    }

    public String showStorePositions(int sessionId, int storeId) {
        return manager.showStorePositions(sessionId,storeId).value.toString();
    }

    public boolean appointOwner(int sessionId, int storeId, int userId) {
        return manager.addStoreOwner(storeId, userId).getError_occurred();
    }

    public boolean removeStore(int sessionId, int storeId){
        return manager.removeStore(storeId).getError_occurred();
    }


    public void clearDatabase() {

    }
}
