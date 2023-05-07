package ServiceLayer;

import BusinessLayer.*;
import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.StoreDTO;

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
    ResponseT<ProductDTO> getProduct(String sessionId, int storeId, int productId);
    ResponseT<List<Product>> getProductsByName(String sessionId, String productName);
    ResponseT<List<ProductDTO>> getProductsByCategory(String sessionId, String productCategory);
    ResponseT<List<ProductDTO>> getProductsBySubstring(String sessionId, String productSubstring);
    ResponseT<List<ProductDTO>> getSearchResults(String sessionId);
    ResponseT<List<ProductDTO>> filterSearchResultsByCategory(String sessionId, String category);
    ResponseT<List<ProductDTO>> filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice);
    Response addProductToCart(String sessionId, int storeId, int productId, int quantity);
    ResponseT<ShoppingCart> getShoppingCart(String sessionId);
    Response changeProductQuantity(String sessionId, int storeId, int productId, int quantity);
    Response removeProductFromCart(String sessionId, int storeId, int productId);
    ResponseT<Purchase> purchaseShoppingCart(String sessionId);
    ResponseT<Integer> openStore(String sessionId, String storeName);
    ResponseT<List<PurchaseDTO>> getPurchaseHistory(String sessionId, int storeId);
    ResponseT<ProductDTO> addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description);
    Response editProductName(String sessionId, int storeId, int productId, String newName);
    Response editProductPrice(String sessionId, int storeId, int productId, int newPrice);
    Response editProductCategory(String sessionId, int storeId, int productId, String newCategory);
    Response removeProductFromStore(String sessionId, int storeId, int productId);
    Response setPositionOfMemberToStoreManager(String sessionId, int storeID, String MemberToBecomeManager);
    Response setPositionOfMemberToStoreOwner(String sessionId, int storeID, String MemberToBecomeOwner);
    Response addStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);
    Response removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);
    ResponseT<List<MemberDTO>> getStoreEmployees(String sessionId, int storeId);
    Response closeStore(String sessionId, int storeId);
    ResponseT<Map<StoreDTO, List<PurchaseDTO>>> getStoresPurchases(String sessionId);

}
