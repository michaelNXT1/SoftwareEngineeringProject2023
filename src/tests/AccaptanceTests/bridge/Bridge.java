package AccaptanceTests.bridge;


import java.time.LocalTime;

public interface Bridge {
    boolean setupSystem(String managerUName, String managerPass);

    String login(String username, String password);

    boolean register(String username, String password);
    boolean getStore(String sessionId, int storeId);

    boolean getProduct(String sessionId, int productID, int storeID);


    boolean closeStore(String sessionId, int storeID);

    Integer getStoresInform(String sessionId, String storeSubString);

    Integer searchProductsByName(String sessionId, String productName);

    Integer searchProductsByCategory(String sessionId, String productCategory);

    Integer searchProductsBySubString(String sessionId, String productSubString);

    Integer filterSearchResultsByCategory(String sessionId, String category);

    Integer filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice);

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



    boolean deleteProduct(String sessionId, int storeId, int productId);


 //   boolean editStorePurchaseType(int sessionId, int storeId, String newType);

    boolean appointManager(String sessionId, int storeId, String userName);

    Integer addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description);

    boolean setProductName(String sessionId, int storeId, int productId, String newName);

    boolean setProductPrice(String sessionId, int storeId, int productId, int newPrice);

    boolean setProductCategory(String sessionId, int storeId, int productId, String newCategory);

    boolean editManagerOptions(String sessionId, String userName, int storeId, int option);

    boolean getStoresPurchases(String sessionId);

    Integer showStorePositions(String sessionId, int storeId);

    boolean editProductPrice(String sessionId, int storeId, int productId, int newPrice);

    boolean editProductCategory(String sessionId, int storeId, int productId, String newCategory);

    boolean editProductName(String sessionId, int storeId, int productId, String newName);

 //   boolean appointOwner(int sessionId, int storeId, String userName);


//    boolean removeManager(int sessionId, int storeId, int userId);

    boolean removeStore(String sessionId, int storeId);

    void clearDatabase();

    String enterMarket();

    boolean exitMarket(String sessionId);

    boolean addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime);
    boolean addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime);
    boolean joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator);
    boolean removePolicy(String sessionId, int storeId, int policyId);
    boolean addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone);
    boolean addMaxQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone);
}
