package ServiceLayer;

import CommunicationLayer.NotificationBroker;
import ServiceLayer.DTOs.*;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IMarketManager {
    Response signUpSystemManager(String username, String password);

    ResponseT<String> enterMarket();

    Response exitMarket(String sessionId);

    Response signUp(String username, String password);

    ResponseT<String> login(String username, String password, NotificationBroker notificationBroker);

    Response logout(String sessionId);

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
    ResponseT<Boolean> hasPermission(String sessionId, int storeId, PositionDTO.permissionType employeeList);
    Response setStoreManagerPermissions(String sessionId, int storeId, String storeManager, Set<PositionDTO.permissionType> permissions);
    Response removeStoreOwner(String sessionId, int storeId, String storeOwnerName);

    ResponseT<Integer> openStore(String sessionId, String storeName);

    ResponseT<List<PurchaseDTO>> getPurchaseHistory(String sessionId, int storeId);

    ResponseT<ProductDTO> addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description);

    Response editProductName(String sessionId, int storeId, int productId, String newName);

    Response editProductPrice(String sessionId, int storeId, int productId, double newPrice);

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

    Response addMaxQuantityPolicy(String sessionId, int storeId, int productId, int maxQuantity);

    Response addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone);

    Response addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType);

    Response addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType);

    Response addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType);

    Response addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone);

    Response addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity, boolean allowNone);

    Response addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal);

    Response joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator);

    Response removeDiscountPolicy(String sessionId, int storeId, int policyId);
    Response removeDiscount(String sessionId, int storeId, int discountId);
    Response addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity);
    ResponseT<List<String>> getDiscountPolicyTypes();
    ResponseT<Set<PositionDTO.permissionType>> getPermissions(String sessionId, int storeId, String username);
    ResponseT<Boolean> hasPaymentMethod(String sessionId);
    ResponseT<Double> getProductDiscountPercentageInCart(String sessionId, int storeId, int productId);

    Response addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv);

    ResponseT<List<String>> getAllCategories();

    Response removeMember(String sessionId, String memberName);

    Response removeStoreOwner(String sessionId, String storeOwnerToRemove, int storeId);

    ResponseT<List<MemberDTO>> getInformationAboutMembers(String sessionId);

    ResponseT<String> getSearchKeyword(String sessionId);

    ResponseT<String> getUsername(String sessionId);

    ResponseT<List<StoreDTO>> getResponsibleStores(String sessionId);

    ResponseT<Boolean> isLoggedIn(String sessionId);

    ResponseT<Map<ProductDTO, Integer>> getProductsByStore(int storeId);

    ResponseT<Map<DiscountDTO, List<BaseDiscountPolicyDTO>>> getDiscountPolicyMap(int storeId);

    ResponseT<List<BasePurchasePolicyDTO>> getPurchasePoliciesByStoreId(int storeId);

    ResponseT<List<String>> getPurchasePolicyTypes();

    Response addSupplyDetails(String sessionId, String name, String address, String city, String country, String zip);

//        Response logoutSystemManager(String sessionId);

//        ResponseT<String> loginSystemManager(String username, String password);
}
