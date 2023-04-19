package AccaptanceTests.bridge;

import org.example.BusinessLayer.Store;
import org.example.ServiceLayer.ResponseT;

public interface Bridge {
    boolean setupSystem(String managerUName, String managerEmail, String managerPass);

    boolean login(String username, String email,String password);

    boolean register(String username, String email,String password);
    boolean getStore(int storeId);

    boolean getProduct(int productID, int storeID);


    String getStoresInform(String storeSubString);

    String searchProductsByName(String productName);

    String searchProductsByCategory(String productCategory);

    String searchProductsBySubString(String productSubString);

    String filterSearchResultsByCategory(String category);

    String filterSearchResultsByPrice(double minPrice, double maxPrice);

    boolean addToCart(int storeId, int productId, int amount);

    boolean updateAmount(int storeId, int productId, int amount);

    boolean deleteItemInCart(int storeId, int productId);

//    boolean clearCart(int sessionId);

    boolean buyCart(int sessionId, String cardNumber, String cardMonth, String cardYear, String cardHolder, String cardCcv, String cardId, String buyerName, String address, String city, String country, String zip);

    String showCart();

    boolean logout();

    boolean openMarket();

    Integer openStore(String storeName);

    String viewPurchaseHistory(int storeID);

    boolean addProductToStore(int sessionId, int productId, int storeId, int amount);


    boolean deleteProduct(int sessionId, int storeId, int productId);


 //   boolean editStorePurchaseType(int sessionId, int storeId, String newType);

    boolean appointManager(int sessionId, int storeId, String userName);

    Integer addProduct(int storeId, String productName, double price, String category, double rating, int quantity);

    boolean setProductName(int storeId, int productId, String newName);

    boolean setProductPrice(int storeId, int productId, int newPrice);

    boolean setProductCategory(int storeId, int productId, String newCategory);

    boolean editManagerOptions(int sessionId, String userName, int storeId, int option);

    String showStorePositions(int sessionId, int storeId);


 //   boolean appointOwner(int sessionId, int storeId, String userName);


//    boolean removeManager(int sessionId, int storeId, int userId);

    boolean removeStore(int storeId);

    void clearDatabase();
}
