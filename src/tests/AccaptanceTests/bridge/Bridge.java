package AccaptanceTests.bridge;

public interface Bridge {
  //  boolean setupSystem(String supplyConfig, String paymentConfig, String path);

    boolean login(String username, String email,String password);

    boolean register(String username, String email,String password);

    String getStoresInform(String storeSubString);

    String searchProductsByName(String productName);

    String searchProductsByCategory(String productCategory);

    String searchProductsBySubString(String productSubString);

    String filter(String filterParams);

    boolean addToCart(int storeId, int productId, int amount);

    boolean updateAmount(int storeId, int productId, int amount);

    boolean deleteItemInCart(int storeId, int productId);

    boolean clearCart(int sessionId);

    boolean buyCart(int sessionId, String cardNumber, String cardMonth, String cardYear, String cardHolder, String cardCcv, String cardId, String buyerName, String address, String city, String country, String zip);

    String showCart(int sessionId);

    boolean logout(int sessionId);

    boolean openStore(int sessionId, String storeName, String newProducts);

    String viewPurchaseHistory(int sessionId, int storeID);

    boolean addProductToStore(int sessionId, int productId, int storeId, int amount);

    boolean editProduct(int sessionId, int storeId, int productId, String productInfo);

    boolean deleteProduct(int sessionId, int storeId, int productId);

    boolean changeBuyingPolicy(int sessionId, int storeId, String newPolicy);

    boolean changeSalesPolicy(int sessionId, int storeId, String newPolicy);

    boolean editStorePurchaseType(int sessionId, int storeId, String newType);

    boolean appointManager(int sessionId, int storeId, int userId);

    boolean editManagerOptions(int sessionId, int storeId, int userId, String option);

    String showStorePositions(int sessionId, int storeId);

    boolean appointOwner(int sessionId, int storeId, int userId);

    boolean editStoreSalesType(int sessionId, int storeId, String newType);

    boolean removeManager(int sessionId, int storeId, int userId);

    boolean removeStore(int sessionId, int storeId);

    void clearDatabase();
}
