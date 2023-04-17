package AccaptanceTests.bridge;

public class ProxyBridge implements Bridge{
    private Bridge real = null;

    public void setRealBridge(Bridge implementation) {
        if (real == null)
            real = implementation;
    }
//    public boolean setupSystem(String supplyConfig, String paymentConfig,String path){
//        if (real != null){
//            return real.setupSystem(supplyConfig, paymentConfig,path);
//        }
//        else{
//            return false;
//        }
//    }

    public boolean login(String username, String email,String password) {
        if (real != null) {
            return real.login(username, email, password);
        }
        else {
            return false;
        }
    }

    public boolean register(String username, String email,String password) {
        if (real != null) {
            return real.register(username, email, password);
        }
        else {
            return false;
        }
    }

    public String getStoresInform(String storeSubString){
        if (real != null) {
            return real.getStoresInform(storeSubString);
        }
        else {
            return null;
        }

    }


    public String searchProductsByName(String productName) {
        if (real != null) {
            return real.searchProductsByName(productName);
        }
        else {
            return null;

        }
    }

    public String searchProductsByCategory(String productCategory) {
        if (real != null) {
            return real.searchProductsByCategory(productCategory);
        }
        else {
            return null;

        }
    }

    public String searchProductsBySubString(String productSubString) {
        if (real != null) {
            return real.searchProductsBySubString(productSubString);
        }
        else {
            return null;

        }
    }

    public String filter(String filterParams) {
        if (real != null) {
            return real.filter(filterParams);
        }
        else {
            return null;

        }
    }

    public boolean addToCart(int storeId, int productId, int amount) {
        if (real != null) {
            return real.addToCart(storeId, productId, amount);
        }
        else {
            return false;

        }
    }

    public boolean updateAmount(int storeId, int productId, int amount) {
        if (real != null) {
            return real.updateAmount(storeId, productId, amount);
        }
        else {
            return false;
        }
    }


    public String showCart(int sessionId){
        if (real != null) {
            return real.showCart(sessionId);
        }
        else {
            return null;
        }

    }


    public boolean deleteItemInCart(int storeId, int productId) {
        if (real != null) {
            return real.deleteItemInCart(storeId, productId);
        }
        else {
            return false;
        }
    }

    public boolean clearCart(int sessionId) {
        if (real != null) {
            return real.clearCart(sessionId);
        }
        else {
            return false;
        }
    }

    public boolean buyCart(int sessionId, String cardNumber, String cardMonth, String cardYear, String cardHolder,
                           String cardCcv, String cardId, String buyerName, String address, String city, String country, String zip) {
        if (real != null) {
            return real.buyCart(sessionId, cardNumber, cardMonth, cardYear, cardHolder, cardCcv, cardId, buyerName, address, city, country, zip);
        }
        else {
            return false;
        }
    }



    public boolean logout(int sessionId){
        if (real != null){
            return real.logout(sessionId);
        }
        else{
            return true;
        }
    }

    public boolean openStore(int sessionId,String storeName, String newProducts){
        if (real != null){
            return real.openStore(sessionId, storeName, newProducts);
        }
        else{
            return false;
        }
    }

    public String viewPurchaseHistory(int sessionId, int storeID){
        if(real != null){
            return real.viewPurchaseHistory(sessionId, storeID);
        }
        else{
            return null;
        }
    }

    public boolean addProductToStore(int sessionId, int productId, int storeId, int amount){
        if(real != null){
            return real.addProductToStore(sessionId, productId, storeId, amount);
        }
        else{
            return false;
        }
    }

    public boolean editProduct(int sessionId, int storeId, int productId, String productInfo){
        if(real != null){
            return real.editProduct(sessionId, storeId, productId, productInfo);
        }
        else{
            return false;
        }
    }

    public boolean deleteProduct(int sessionId, int storeId, int productId){
        if(real != null){
            return real.deleteProduct(sessionId, storeId, productId);
        }
        else{
            return false;
        }
    }


    public boolean changeBuyingPolicy(int sessionId,  int storeId, String newPolicy) {
        if(real != null){
            return real.changeBuyingPolicy(sessionId, storeId, newPolicy);
        }
        else return false;
    }

    public boolean changeSalesPolicy(int sessionId,  int storeId, String newPolicy) {
        if(real != null){
            return real.changeSalesPolicy(sessionId, storeId, newPolicy);
        }
        else return false;
    }

    public boolean editStorePurchaseType(int sessionId, int storeId, String newType) {
        if(real != null){
            return real.editStorePurchaseType(sessionId, storeId, newType);
        }
        else return false;
    }

    public boolean editStoreSalesType(int sessionId, int storeId, String newType) {
        if(real != null){
            return real.editStoreSalesType(sessionId, storeId, newType);
        }
        else return false;
    }


//    public String searchUserHistory(int sessionId, int userId){
//        if(real != null){
//            return real.searchUserHistory(sessionId, userId);
//        }
//        else{
//            return null;
//        }
//    }


    public boolean appointManager(int sessionId, int storeId, int userId){
        if(real != null){
            return real.appointManager(sessionId, storeId, userId);
        }
        else{
            return false;
        }
    }


    public boolean editManagerOptions(int sessionId, int storeId, int userId, String option){
        if(real != null){
            return real.editManagerOptions(sessionId, storeId, userId, option);
        }
        else{
            return false;
        }
    }


    public String showStorePositions(int sessionId, int storeId){
        if(real != null){
            return real.showStorePositions(sessionId, storeId);
        }
        else{
            return null;
        }
    }

    public boolean appointOwner(int sessionId, int storeId, int userId){
        if(real != null){
            return real.appointOwner(sessionId, storeId, userId);
        }
        else{
            return false;
        }
    }

    public boolean removeManager(int sessionId, int storeId, int userId){
        if(real != null){
            return real.removeManager(sessionId, storeId, userId);
        }
        else{
            return false;
        }
    }

    public boolean removeStore(int sessionId, int storeId){
        if(real != null){
            return real.removeStore(sessionId, storeId);
        }
        else{
            return false;
        }
    }

}
