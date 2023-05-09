package CommunicationLayer;

import ServiceLayer.DTOs.*;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface IMarketController {
    boolean signUpSystemManager(String username, String password);
    boolean enterMarket();
    boolean exitMarket(String sessionId);
    boolean signUp(String username, String password);
    String login(String username, String password);
    boolean logout(String sessionId);
    List<StoreDTO> getStores(String sessionId, String storeSubString);
    StoreDTO getStore(String sessionId, int storeId);
    ProductDTO getProduct(String sessionId, int storeId, int productId);
    List<ProductDTO> getProductsByName(String sessionId, String productName);
    List<ProductDTO> getProductsByCategory(String sessionId, String productCategory);
    List<ProductDTO> getProductsBySubstring(String sessionId, String productSubstring);
    List<ProductDTO> getSearchResults(String sessionId);
    List<ProductDTO> filterSearchResultsByCategory(String sessionId, String category);
    List<ProductDTO> filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice);
    boolean addProductToCart(String sessionId, int storeId, int productId, int quantity);
    ShoppingCartDTO getShoppingCart(String sessionId);
    boolean changeProductQuantity(String sessionId, int storeId, int productId, int quantity);
    boolean removeProductFromCart(String sessionId, int storeId, int productId);
    PurchaseDTO purchaseShoppingCart(String sessionId);
    Integer openStore(String sessionId, String storeName);
    List<PurchaseDTO> getPurchaseHistory(String sessionId, int storeId);
    ProductDTO addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description);
    boolean editProductName(String sessionId, int storeId, int productId, String newName);
    boolean editProductPrice(String sessionId, int storeId, int productId, int newPrice);
    boolean editProductCategory(String sessionId, int storeId, int productId, String newCategory);
    boolean removeProductFromStore(String sessionId, int storeId, int productId);
    boolean setPositionOfMemberToStoreManager(String sessionId, int storeID, String MemberToBecomeManager);
    boolean setPositionOfMemberToStoreOwner(String sessionId, int storeID, String MemberToBecomeOwner);
    boolean addStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);
    boolean removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);
    List<MemberDTO> getStoreEmployees(String sessionId, int storeId);
    boolean closeStore(String sessionId, int storeId);
    Map<StoreDTO, List<PurchaseDTO>> getStoresPurchases(String sessionId);
    boolean addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime);
    boolean addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime);
    boolean joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator);
    boolean removePolicy(String sessionId, int storeId, int policyId);
    boolean addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone);
    boolean addMaxQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone);

    List<String> getAllCategories();

}
