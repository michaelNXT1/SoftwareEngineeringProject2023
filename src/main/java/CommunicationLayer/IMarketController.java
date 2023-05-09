package CommunicationLayer;

import ServiceLayer.DTOs.*;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface IMarketController {
    Response signUpSystemManager(String username, String password);
    Response enterMarket();
    Response exitMarket(String sessionId);
    Response signUp(String username, String password);
    ResponseT<String> login(String username, String password);
    ResponseT<String> loginSystemManager(String username, String password);
    Response logout(String sessionId);
    Response logoutSystemManager(String sessionId);
    ResponseT<List<StoreDTO>> getStores(String sessionId, String storeSubString);
    ResponseT<StoreDTO> getStore(String sessionId, int storeId);
    ResponseT<ProductDTO> getProduct(String sessionId, int storeId, int productId);
    ResponseT<List<ProductDTO>> getProductsByName(String sessionId, String productName);
    ResponseT<List<ProductDTO>> getProductsByCategory(String sessionId, String productCategory);
    ResponseT<List<ProductDTO>> getProductsBySubstring(String sessionId, String productSubstring);
    ResponseT<List<ProductDTO>> getSearchResults(String sessionId);
    ResponseT<List<ProductDTO>> filterSearchResultsByCategory(String sessionId, String category);
    ResponseT<List<ProductDTO>> filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice);
    Response addProductToCart(String sessionId, int storeId, int productId, int quantity);
    ResponseT<ShoppingCartDTO> getShoppingCart(String sessionId);
    Response changeProductQuantity(String sessionId, int storeId, int productId, int quantity);
    Response removeProductFromCart(String sessionId, int storeId, int productId);
    ResponseT<PurchaseDTO> purchaseShoppingCart(String sessionId);
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
    Response addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime);
    Response addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime);
    Response joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator);
    Response removePolicy(String sessionId, int storeId, int policyId);
    Response addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone);
    Response addMaxQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone);

}
