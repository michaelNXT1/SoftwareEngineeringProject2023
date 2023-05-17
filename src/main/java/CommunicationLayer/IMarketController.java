package CommunicationLayer;

import ServiceLayer.DTOs.*;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface IMarketController {
    boolean signUpSystemManager(String username, String password);

    String enterMarket();

    boolean exitMarket(String sessionId);

    boolean signUp(String username, String password);

    String login(String username, String password);

    ResponseT<String> logout(String sessionId);

    List<StoreDTO> getStores(String sessionId, String storeSubString);

    StoreDTO getStore(String sessionId, int storeId);

    ProductDTO getProduct(String sessionId, int storeId, int productId);

    List<ProductDTO> getProductsByName(String sessionId, String productName);

    List<ProductDTO> getProductsByCategory(String sessionId, String productCategory);

    List<ProductDTO> getProductsBySubstring(String sessionId, String productSubstring);

    List<ProductDTO> getSearchResults(String sessionId);

    List<ProductDTO> filterSearchResultsByCategory(String sessionId, String category);

    List<ProductDTO> filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice);

    Response addProductToCart(String sessionId, int storeId, int productId, int quantity);

    ShoppingCartDTO getShoppingCart(String sessionId);

    Response changeProductQuantity(String sessionId, int storeId, int productId, int quantity);

    Response removeProductFromCart(String sessionId, int storeId, int productId);

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

    Response addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType);

    Response addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType);

    Response addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType);

    Response addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone);

    Response addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity, boolean allowNone);

    Response addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal);

    Response joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator);

    Response removeDiscountPolicy(String sessionId, int storeId, int policyId);

    List<String> getAllCategories();

    boolean addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv);

    String getSearchKeyword(String sessionId);

    String getUsername(String sessionId);

    List<StoreDTO> getResponsibleStores(String sessionId);

    ResponseT<Boolean> isLoggedIn(String sessionId);

    ResponseT<Map<ProductDTO, Integer>> getProductsByStore(int storeId);

    ResponseT<Map<DiscountDTO, List<BaseDiscountPolicyDTO>>> getDiscountPolicyMap(int storeId);
}
