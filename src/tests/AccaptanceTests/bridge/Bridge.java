package AccaptanceTests.bridge;


public interface Bridge {
    boolean setupSystem(String managerUName, String managerPass);

    String login(String username, String password);

    boolean register(String username, String password);
    boolean getStore(String sessionId, int storeId);

    boolean getProduct(String sessionId, int productID, int storeID);


    boolean closeStore(String sessionId, int storeID);

    String getStoresInform(String sessionId, String storeSubString);

    String searchProductsByName(String sessionId, String productName);

    String searchProductsByCategory(String sessionId, String productCategory);

    String searchProductsBySubString(String sessionId, String productSubString);

    String filterSearchResultsByCategory(String sessionId, String category);

    String filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice);

    boolean addToCart(String sessionId, int storeId, int productId, int amount);

    boolean updateAmount(String sessionId, int storeId, int productId, int amount);

    boolean deleteItemInCart(String sessionId, int storeId, int productId);

//    boolean clearCart(int sessionId);

    boolean buyCart(String sessionId, String cardNumber, String cardMonth, String cardYear, String cardHolder, String cardCcv, String cardId, String buyerName, String address, String city, String country, String zip);

    String showCart(String sessionId);

    boolean logout(String sessionId);

    boolean openMarket();

    Integer openStore(String sessionId, String storeName);

    String viewPurchaseHistory(String sessionId, int storeID);

    boolean addProductToStore(String sessionId, int productId, int storeId, int amount);


    boolean deleteProduct(String sessionId, int storeId, int productId);


 //   boolean editStorePurchaseType(int sessionId, int storeId, String newType);

    boolean appointManager(String sessionId, int storeId, String userName);

    Integer addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description);

    boolean setProductName(String sessionId, int storeId, int productId, String newName);

    boolean setProductPrice(String sessionId, int storeId, int productId, int newPrice);

    boolean setProductCategory(String sessionId, int storeId, int productId, String newCategory);

    boolean editManagerOptions(String sessionId, String userName, int storeId, int option);

    boolean getStoresPurchases(String sessionId);

    String showStorePositions(String sessionId, int storeId);

    boolean editProductPrice(String sessionId, int storeId, int productId, int newPrice);

    boolean editProductCategory(String sessionId, int storeId, int productId, String newCategory);

    boolean editProductName(String sessionId, int storeId, int productId, String newName);

 //   boolean appointOwner(int sessionId, int storeId, String userName);


//    boolean removeManager(int sessionId, int storeId, int userId);

    boolean removeStore(String sessionId, int storeId);

    void clearDatabase();
}
