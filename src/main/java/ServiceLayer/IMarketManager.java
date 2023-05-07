package ServiceLayer;

import BusinessLayer.*;

import java.util.List;
import java.util.Map;

public interface IMarketManager {
    Response signUpSystemManager(String username, String password);
    Response enterMarket();
    Response exitMarket(String sessionId);
    Response signUp(String username, String password);
    ResponseT<String> login(String username, String password);
    ResponseT<String> loginSystemManager(String username, String password);
    Response logout(String sessionId);
    Response logoutSystemManager(String sessionId);
    ResponseT<List<Store>> getStores(String sessionId, String storeSubString);
    ResponseT<Store> getStore(String sessionId, int storeId);
    ResponseT<Product> getProduct(String sessionId, int storeId, int productId);
    ResponseT<List<Product>> getProductsByName(String sessionId, String productName);
    ResponseT<List<Product>> getProductsByCategory(String sessionId, String productCategory);
    ResponseT<List<Product>> getProductsBySubstring(String sessionId, String productSubstring);
    ResponseT<List<Product>> getSearchResults(String sessionId);
    ResponseT<List<Product>> filterSearchResultsByCategory(String sessionId, String category);
    ResponseT<List<Product>> filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice);
    Response addProductToCart(String sessionId, int storeId, int productId, int quantity);
    ResponseT<ShoppingCart> getShoppingCart(String sessionId);
    Response changeProductQuantity(String sessionId, int storeId, int productId, int quantity);
    Response removeProductFromCart(String sessionId, int storeId, int productId);
    ResponseT<Purchase> purchaseShoppingCart(String sessionId);
    ResponseT<Integer> openStore(String sessionId, String storeName);
    ResponseT<List<Purchase>> getPurchaseHistory(String sessionId, int storeId);
    ResponseT<Product> addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description);
    Response editProductName(String sessionId, int storeId, int productId, String newName);
    Response editProductPrice(String sessionId, int storeId, int productId, int newPrice);
    Response editProductCategory(String sessionId, int storeId, int productId, String newCategory);
    Response removeProductFromStore(String sessionId, int storeId, int productId);
    Response setPositionOfMemberToStoreManager(String sessionId, int storeID, String MemberToBecomeManager);
    Response setPositionOfMemberToStoreOwner(String sessionId, int storeID, String MemberToBecomeOwner);
    Response addStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);
    Response removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);
    ResponseT<List<Member>> getStoreEmployees(String sessionId, int storeId);
    Response closeStore(String sessionId, int storeId);
    ResponseT<Map<Store, List<Purchase>>> getStoresPurchases(String sessionId);

}
