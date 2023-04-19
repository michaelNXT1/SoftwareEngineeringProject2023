package AccaptanceTests.bridge;

public class ProxyBridge implements Bridge{
    private Bridge real = null;

    public void setRealBridge(Bridge implementation) {
        if (real == null)
            real = implementation;
    }
    public boolean setupSystem(String managerUName, String managerEmail, String managerPass){
        if (real != null){
            return real.setupSystem(managerUName, managerEmail,managerPass);
        }
        else{
            return false;
        }
    }



    public boolean login(String username, String email, String password) {
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


    public boolean getStore(int storeId) {
        if (real != null) {
            return real.getStore(storeId);
        }
        else {
            return false;
        }
    }

    @Override
    public boolean getProduct(int productID, int storeID) {
        if (real != null) {
            return real.getProduct(productID, storeID);
        }
        else {
            return false;
        }
    }

    @Override
    public boolean closeStore(int storeID) {
        if (real != null) {
            return real.closeStore(storeID);
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

    public String filterSearchResultsByCategory(String category) {
        if (real != null) {
            return real.filterSearchResultsByCategory(category);
        }
        else {
            return null;

        }
    }

    public String filterSearchResultsByPrice(double minPrice, double maxPrice) {
        if (real != null) {
            return real.filterSearchResultsByPrice(minPrice, maxPrice);
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


    public String showCart(){
        if (real != null) {
            return real.showCart();
        }
        else {
            return null;
        }

    }


    public boolean logout() {
        if (real != null) {
            return real.logout();
        }
        else {
            return false;
        }
    }

    public boolean openMarket() {
        if(real != null)
            return real.openMarket();
        else
            return false;
    }


    public boolean deleteItemInCart(int storeId, int productId) {
        if (real != null) {
            return real.deleteItemInCart(storeId, productId);
        }
        else {
            return false;
        }
    }

//    public boolean clearCart(int sessionId) {
//        if (real != null) {
//            return real.clearCart(sessionId);
//        }
//        else {
//            return false;
//        }
//    }

    public boolean buyCart(int sessionId, String cardNumber, String cardMonth, String cardYear, String cardHolder,
                           String cardCcv, String cardId, String buyerName, String address, String city, String country, String zip) {
        if (real != null) {
            return real.buyCart(sessionId, cardNumber, cardMonth, cardYear, cardHolder, cardCcv, cardId, buyerName, address, city, country, zip);
        }
        else {
            return false;
        }
    }




    public Integer openStore(String storeNames){
        if (real != null){
            return real.openStore(storeNames);
        }
        else{
            return -1;
        }
    }


    public String viewPurchaseHistory(int storeID) {
        if (real != null) {
            return real.viewPurchaseHistory(storeID);
        }
        else {
            return null;
        }
    }

    public String viewPurchaseHistory(int sessionId, int storeID){
        if(real != null){
            return real.viewPurchaseHistory(storeID);
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


    public boolean deleteProduct(int sessionId, int storeId, int productId){
        if(real != null){
            return real.deleteProduct(sessionId, storeId, productId);
        }
        else{
            return false;
        }
    }



//    public boolean editStorePurchaseType(int sessionId, int storeId, String newType) {
//        if(real != null){
//            return real.editStorePurchaseType(sessionId, storeId, newType);
//        }
//        else return false;
//    }




//    public String searchUserHistory(int sessionId, int userId){
//        if(real != null){
//            return real.searchUserHistory(sessionId, userId);
//        }
//        else{
//            return null;
//        }
//    }


    public boolean appointManager(int sessionId, int storeId, String userName){
        if(real != null){
            return real.appointManager(sessionId, storeId, userName);
        }
        else{
            return false;
        }
    }


    public Integer addProduct(int storeId, String productName, double price, String category, double rating, int quantity) {
        if (real != null) {
            return real.addProduct(storeId, productName, price, category, rating, quantity);
        }
        else {
            return -1;
        }
    }


    public boolean setProductName(int storeId, int productId, String newName) {
        if (real != null) {
            return real.setProductName(storeId, productId, newName);
        }
        else {
            return false;
        }
    }


    public boolean setProductPrice(int storeId, int productId, int newPrice) {
        if (real != null) {
            return real.setProductPrice(storeId, productId, newPrice);
        }
        else {
            return false;
        }
    }


    public boolean setProductCategory(int storeId, int productId, String newCategory) {
        if (real != null) {
            return real.setProductCategory(storeId, productId, newCategory);
        }
        else {
            return false;
        }
    }


    public boolean setProductName(String newName) {
        return false;
    }


    public boolean editManagerOptions(int sessionId, String userName, int storeId, int option){
        if(real != null){
            return real.editManagerOptions(sessionId, userName, storeId, option);
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

    @Override
    public boolean editProductPrice(int storeId, int productId, int newPrice) {
        if(real != null){
            return real.editProductPrice(storeId, productId, newPrice);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean editProductCategory(int storeId, int productId, String newCategory) {
        if(real != null){
            return real.editProductCategory(storeId, productId, newCategory);
        }
        else{
            return false;
        }
    }

    @Override
    public boolean editProductName(int storeId, int productId, String newName) {
        if(real != null){
            return real.editProductName(storeId, productId, newName);
        }
        else{
            return false;
        }
    }

//    public boolean appointOwner(int sessionId, int storeId, String userName){
//        if(real != null){
//            return real.appointOwner(sessionId, storeId, userName);
//        }
//        else{
//            return false;
//        }
//    }

//    public boolean removeManager(int sessionId, int storeId, int userId){
//        if(real != null){
//            return real.removeManager(sessionId, storeId, userId);
//        }
//        else{
//            return false;
//        }
//    }

    public boolean removeStore(int storeId){
        if(real != null){
            return real.removeStore(storeId);
        }
        else{
            return false;
        }
    }


    public void clearDatabase() {
        if(real != null){
            real.clearDatabase();
        }
        else{

        }
    }

}
