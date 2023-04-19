package AccaptanceTests.bridge;


import org.example.ServiceLayer.MarketManager;

public class Real implements Bridge {

    private MarketManager manager;

    public Real (){
         this.manager = new MarketManager();
    }

    public boolean setupSystem(String managerUName, String managerEmail, String managerPass){
        return !this.manager.signUpSystemManager(managerUName, managerEmail, managerPass).getError_occurred();
    }

    public boolean login(String username,String email,String password) {
        return !manager.login(username, email, password).getError_occurred();
    }

    public boolean register(String username,String email,String password) {
        return !manager.signUp(username, email,password).getError_occurred();
    }

    @Override
    public boolean getStore(int storeId) {
        return !manager.getStore(storeId).getError_occurred();
    }

    @Override
    public boolean getProduct(int productID, int storeID) {
        return !manager.getProduct(productID, storeID).getError_occurred();
    }

    @Override
    public boolean closeStore(int storeID) {
        return !this.manager.closeStore(storeID).getError_occurred();
    }

    public String getStoresInform(String storeSubString) {
        return manager.getStores(storeSubString).value.toString();
    }

    public String searchProductsByName(String productName) {
        return manager.getProductsByName(productName).value.toString();
    }

    public String searchProductsByCategory(String productCategory) {
        return manager.getProductsByCategory(productCategory).value.toString();
    }

    public String searchProductsBySubString(String productSubString) {
        return manager.getProductsBySubstring(productSubString).value.toString();
    }


    public String filterSearchResultsByCategory(String filterParams){
        return manager.filterSearchResultsByCategory(filterParams).value.toString();
    }

    @Override
    public String filterSearchResultsByPrice(double minPrice, double maxPrice) {
        return manager.filterSearchResultsByPrice(minPrice, maxPrice).value.toString();
    }


    public boolean addToCart(int storeId, int productId, int amount) {
        return !manager.addProductToCart(storeId, productId, amount).getError_occurred();
    }


    public String showCart(){
        return manager.getShoppingCart().value.toString();
    }


    public boolean updateAmount(int storeId,int productId, int amount) {
        return !manager.changeProductQuantity(storeId, productId, amount).getError_occurred();
    }

    public boolean deleteItemInCart(int storeId, int productId) {
        return !manager.removeProductFromCart(storeId, productId).getError_occurred();
    }

//    public boolean clearCart(int sessionId) {
//        return manager.clearCart(sessionId).getError_occurred();
//    }

    public boolean buyCart(int sessionId, String cardNumber, String cardMonth, String cardYear, String cardHolder,
                           String cardCcv, String cardId, String buyerName, String address, String city, String country, String zip) {
        return !manager.purchaseShoppingCart().getError_occurred();
    }


    public boolean logout(){
        return !manager.logout().getError_occurred();

    }

    @Override
    public boolean openMarket() {
        return !this.manager.enterMarket().getError_occurred();
    }


    public Integer openStore(String storeName) {
        return manager.openStore(storeName).value;
    }

    public String viewPurchaseHistory(int storeID){
        return manager.getPurchaseHistory(storeID).value.toString();
    }


    public boolean addProductToStore(int sessionId, int productId, int storeId, int amount) {
        return !manager.addProductToCart(productId,storeId,amount).getError_occurred();
    }


    public boolean deleteProduct(int sessionId, int storeId, int productId) {
        return !manager.removeProductFromStore(storeId, productId).getError_occurred();
    }




    public boolean appointManager(int sessionId, int storeId, String userName) {
        return !manager.setPositionOfMemberToStoreManager(storeId, userName).getError_occurred();
    }


    public Integer addProduct(int storeId, String productName, double price, String category, double rating, int quantity) {
        return manager.addProduct(storeId, productName, price, category, rating, quantity).value.getProductId();
    }


    public boolean setProductName(int storeId, int productId, String newName) {
        return !manager.editProductName(storeId, productId, newName).getError_occurred();
    }


    public boolean setProductPrice(int storeId, int productId, int newPrice) {
        return !manager.editProductPrice(storeId, productId, newPrice).getError_occurred();
    }


    public boolean setProductCategory(int storeId, int productId, String newCategory) {
        return !manager.editProductCategory(storeId, productId, newCategory).getError_occurred();
    }


    public boolean setProductName(String newName) {
        return false;
    }

//    public boolean removeManager(int sessionId, int storeId, int userId) {
//        return manager.deleteManager(storeId, userId).getError_occurred();
//    }

    public boolean editManagerOptions(int sessionId, String userName, int storeId, int option){
        return !manager.addStoreManagerPermissions(userName, storeId, option).getError_occurred();
    }

    public String showStorePositions(int sessionId, int storeId) {
        return manager.getStoreEmployees(storeId).value.toString();
    }

    @Override
    public boolean editProductPrice(int storeId, int productId, int newPrice) {
        return !manager.editProductPrice(storeId, productId, newPrice).getError_occurred();
    }

    @Override
    public boolean editProductCategory(int storeId, int productId, String newCategory) {
        return !manager.editProductCategory(storeId, productId, newCategory).getError_occurred();    }

    @Override
    public boolean editProductName(int storeId, int productId, String newName) {
        return !manager.editProductName(storeId, productId, newName).getError_occurred();    }

//    public boolean appointOwner(int sessionId, int storeId, int userId) {
//        return manager.addStoreOwner(storeId, userId).getError_occurred();
//    }

    public boolean removeStore(int storeID){
        return manager.closeStore(storeID).getError_occurred();
    }


    public void clearDatabase() {

    }
}
