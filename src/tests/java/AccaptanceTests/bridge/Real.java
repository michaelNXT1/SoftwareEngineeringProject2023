package AccaptanceTests.bridge;


import ServiceLayer.MarketManager;

public class Real implements Bridge {

    private MarketManager manager;

    public Real (){
         this.manager = new MarketManager();
    }

    public boolean setupSystem(String managerUName, String managerPass){
        return !this.manager.signUpSystemManager(managerUName, managerPass).getError_occurred();
    }

    public boolean login(String username,String password) {
        return !manager.login(username, password).getError_occurred();
    }

    public boolean register(String username,String password) {
        return !manager.signUp(username, password).getError_occurred();
    }

    @Override
    public boolean getStore(String sessionId, int storeId) {
        return !manager.getStore(sessionId, storeId).getError_occurred();
    }

    @Override
    public boolean getProduct(String sessionId, int productID, int storeID) {
        return !manager.getProduct(sessionId, storeID, productID).getError_occurred();
    }

    @Override
    public boolean closeStore(String sessionId, int storeID) {
        return !this.manager.closeStore(sessionId, storeID).getError_occurred();
    }

    public String getStoresInform(String sessionId, String storeSubString) {
        return manager.getStores(sessionId, storeSubString).value.toString();
    }

    public String searchProductsByName(String sessionId, String productName) {
        return manager.getProductsByName(sessionId, productName).value.toString();
    }

    public String searchProductsByCategory(String sessionId, String productCategory) {
        return manager.getProductsByCategory(sessionId, productCategory).value.toString();
    }

    public String searchProductsBySubString(String sessionId, String productSubString) {
        return manager.getProductsBySubstring(sessionId, productSubString).value.toString();
    }


    public String filterSearchResultsByCategory(String sessionId, String filterParams){
        return manager.filterSearchResultsByCategory(sessionId, filterParams).value.toString();
    }

    @Override
    public String filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice) {
        return manager.filterSearchResultsByPrice(sessionId, minPrice, maxPrice).value.toString();
    }


    public boolean addToCart(String sessionId, int storeId, int productId, int amount) {
        return !manager.addProductToCart(sessionId, storeId, productId, amount).getError_occurred();
    }


    public String showCart(String sessionId){
        return manager.getShoppingCart(sessionId).value.toString();
    }

    public boolean updateAmount(String sessionId, int storeId,int productId, int amount) {
        return !manager.changeProductQuantity(sessionId, storeId, productId, amount).getError_occurred();
    }

    public boolean deleteItemInCart(String sessionId, int storeId, int productId) {
        return !manager.removeProductFromCart(sessionId, storeId, productId).getError_occurred();
    }

//    public boolean clearCart(int sessionId) {
//        return manager.clearCart(sessionId).getError_occurred();
//    }

    public boolean buyCart(String sessionId, String cardNumber, String cardMonth, String cardYear, String cardHolder,
                           String cardCcv, String cardId, String buyerName, String address, String city, String country, String zip) {
        return !manager.purchaseShoppingCart(sessionId).getError_occurred();
    }


    public boolean logout(String sessionId){
        return !manager.logout(sessionId).getError_occurred();
    }

    @Override
    public boolean openMarket() {
        return !this.manager.enterMarket().getError_occurred();
    }


    public Integer openStore(String sessionId, String storeName) {
        return manager.openStore(sessionId, storeName).value;
    }

    public String viewPurchaseHistory(String sessionId, int storeID){
        return manager.getPurchaseHistory(sessionId, storeID).value.toString();
    }


    public boolean addProductToStore(String sessionId, int productId, int storeId, int amount) {
        return !manager.addProductToCart(sessionId, productId,storeId,amount).getError_occurred();
    }


    public boolean deleteProduct(String sessionId, int storeId, int productId) {
        return !manager.removeProductFromStore(sessionId, storeId, productId).getError_occurred();
    }




    public boolean appointManager(String sessionId, int storeId, String userName) {
        return !manager.setPositionOfMemberToStoreManager(sessionId, storeId, userName).getError_occurred();
    }


    public Integer addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description) {
        return manager.addProduct(sessionId, storeId, productName, price, category, quantity,description).value.getProductId();
    }


    public boolean setProductName(String sessionId, int storeId, int productId, String newName) {
        return !manager.editProductName(sessionId, storeId, productId, newName).getError_occurred();
    }


    public boolean setProductPrice(String sessionId, int storeId, int productId, int newPrice) {
        return !manager.editProductPrice(sessionId, storeId, productId, newPrice).getError_occurred();
    }


    public boolean setProductCategory(String sessionId, int storeId, int productId, String newCategory) {
        return !manager.editProductCategory(sessionId, storeId, productId, newCategory).getError_occurred();
    }


    public boolean setProductName(String sessionId, String newName) {
        return false;
    }

//    public boolean removeManager(int sessionId, int storeId, int userId) {
//        return manager.deleteManager(storeId, userId).getError_occurred();
//    }

    public boolean editManagerOptions(String sessionId, String userName, int storeId, int option){
        return !manager.addStoreManagerPermissions(sessionId, userName, storeId, option).getError_occurred();
    }

    @Override
    public boolean getStoresPurchases(String sessionId) {
        return !this.manager.getStoresPurchases(sessionId).getError_occurred();
    }

    public String showStorePositions(String sessionId, int storeId) {
        return manager.getStoreEmployees(sessionId, storeId).value.toString();
    }

    @Override
    public boolean editProductPrice(String sessionId, int storeId, int productId, int newPrice) {
        return !manager.editProductPrice(sessionId, storeId, productId, newPrice).getError_occurred();
    }

    @Override
    public boolean editProductCategory(String sessionId, int storeId, int productId, String newCategory) {
        return !manager.editProductCategory(sessionId, storeId, productId, newCategory).getError_occurred();    }

    @Override
    public boolean editProductName(String sessionId, int storeId, int productId, String newName) {
        return !manager.editProductName(sessionId, storeId, productId, newName).getError_occurred();    }

//    public boolean appointOwner(int sessionId, int storeId, int userId) {
//        return manager.addStoreOwner(storeId, userId).getError_occurred();
//    }

    public boolean removeStore(String sessionId, int storeID){
        return manager.closeStore(sessionId, storeID).getError_occurred();
    }


    public void clearDatabase() {

    }
}
