package CommunicationLayer;

import ServiceLayer.DTOs.*;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IMarketController {
    Response
    signUpSystemManager(String username, String password);

    ResponseT<String>
    enterMarket();

    Response
    exitMarket(String sessionId);

    Response
    signUp(String username, String password);

    ResponseT<String>
    login(String username, String password);
    ResponseT<String>
    loginSystemManager(String username, String password);

    ResponseT<String>
    logout(String sessionId);

    ResponseT<List<StoreDTO>>
    getStores(String sessionId, String storeSubString);

    ResponseT<StoreDTO>
    getStore(String sessionId, int storeId);

    ResponseT<ProductDTO>
    getProduct(String sessionId, int storeId, int productId);

    ResponseT<List<ProductDTO>>
    getProductsByName(String sessionId, String productName);

    ResponseT<List<ProductDTO>>
    getProductsByCategory(String sessionId, String productCategory);

    ResponseT<List<ProductDTO>>
    getProductsBySubstring(String sessionId, String productSubstring);

    ResponseT<List<ProductDTO>>
    getSearchResults(String sessionId);

    ResponseT<List<ProductDTO>>
    filterSearchResultsByCategory(String sessionId, String category);

    ResponseT<List<ProductDTO>>
    filterSearchResultsByPrice(String sessionId, double minPrice, double maxPrice);

    Response
    addProductToCart(String sessionId, int storeId, int productId, int quantity);

    ResponseT<ShoppingCartDTO>
    getShoppingCart(String sessionId);

    Response
    changeProductQuantity(String sessionId, int storeId, int productId, int quantity);

    Response
    removeProductFromCart(String sessionId, int storeId, int productId);

    ResponseT<PurchaseDTO>
    purchaseShoppingCart(String sessionId);

    ResponseT<Integer>
    openStore(String sessionId, String storeName);

    ResponseT<List<PurchaseDTO>>
    getPurchaseHistory(String sessionId, int storeId);

    ResponseT<ProductDTO>
    addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description, ProductDTO.PurchaseType purchaseType);

    Response
    editProductName(String sessionId, int storeId, int productId, String newName);

    Response
    editProductPrice(String sessionId, int storeId, int productId, double newPrice);

    Response
    editProductCategory(String sessionId, int storeId, int productId, String newCategory);

    Response
    removeProductFromStore(String sessionId, int storeId, int productId);

    Response
    setPositionOfMemberToStoreManager(String sessionId, int storeID, String MemberToBecomeManager);

    Response
    setPositionOfMemberToStoreOwner(String sessionId, int storeID, String MemberToBecomeOwner);

    Response
    addStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);

    Response
    removeMember(String sessionId, String memberName);


    Response
    removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);

    ResponseT<List<MemberDTO>>
    getStoreEmployees(String sessionId, int storeId);

    Response
    closeStore(String sessionId, int storeId);

    ResponseT<Map<StoreDTO, List<PurchaseDTO>>>
    getStoresPurchases(String sessionId);

    Response
    addProductTimeRestrictionPolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime);

    Response
    addCategoryTimeRestrictionPolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime);

    Response
    joinPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator);

    Response
    removePolicy(String sessionId, int storeId, int policyId);

    Response
    addMinQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone);

    Response
    addMaxQuantityPolicy(String sessionId, int storeId, int productId, int minQuantity);

    ResponseT<Long>
    addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType);

    ResponseT<Long>
    addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType);

    ResponseT<List<MemberDTO>>
    getInformationAboutMembers(String sessionId);

    ResponseT<Long>
    addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType);

    Response
    addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone);

    Response
    addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity);

    Response
    addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal);

    Response
    joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator);

    Response
    removeDiscountPolicy(String sessionId, int storeId, int policyId);

    ResponseT<List<String>>
    getAllCategories();

    Response
    addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv);

    ResponseT<String>
    getSearchKeyword(String sessionId);

    ResponseT<String>
    getUsername(String sessionId);

    ResponseT<List<StoreDTO>>
    getResponsibleStores(String sessionId);

    ResponseT<Boolean>
    isLoggedIn(String sessionId);

    ResponseT<Map<ProductDTO, Integer>>
    getProductsByStore(int storeId);

    ResponseT<Map<DiscountDTO, List<BaseDiscountPolicyDTO>>>
    getDiscountPolicyMap(int storeId);

    ResponseT<List<BasePurchasePolicyDTO>>
    getPurchasePoliciesByStoreId(int storeId);

    ResponseT<List<String>>
    getPurchasePolicyTypes();

    Response
    removeDiscount(String sessionId, int storeId, int discountId);

    ResponseT<List<String>>
    getDiscountPolicyTypes();

    ResponseT<Boolean>
    hasPermission(String sessionId, int storeId, PositionDTO.permissionType employeeList);

    Response
    removeStoreOwner(String sessionId, int storeId, String username);

    Response
    setStoreManagerPermissions(String sessionId, int storeId, String username, Set<PositionDTO.permissionType> mapPermissions);

    ResponseT<Set<PositionDTO.permissionType>>
    getPermissions(String sessionId, int storeId, String username);

    ResponseT<Boolean> hasPaymentMethod(String sessionId);

    ResponseT<Double> getProductDiscountPercentageInCart(String sessionId, int storeId, int productId);

    Response addSupplyDetails(String sessionId, String name, String address, String city, String country, String zip);

    ResponseT<List<PurchaseDTO>> getUserPurchaseHistory(String sessionId);

    Response makeOffer(String sessionId, int storeId, int productId, Double pricePerItem, Integer quantity);

    ResponseT<Boolean> hasDeliveryAddress(String sessionId);

    Response editProductDescription(String sessionId, int storeId, int productId, String value);

    ResponseT<List<OfferDTO>> getOffersByStore(int storeId);

    Response rejectOffer(String sessionId, int storeId, int offerId);

    Response acceptOffer(String sessionId, int storeId, int offerId);

    ResponseT<ProductDTO> addAuctionProduct(String sessionId, int storeId, String productName, Double price, String category, Integer quantity, String description, LocalDateTime auctionEndDateTime);

    Response bid(String sessionId, int storeId, int productId, Double price);

    Response confirmAuction(String sessionId, int storeId, int productId);

    Response requestSetPositionOfMemberToStoreManager(String sessionId, int storeId, String memberToBecomeManager);

    Response requestSetPositionOfMemberToStoreOwner(String sessionId, int storeId, String memberToBecomeOwner);

    Response rejectRequest(String sessionId, int storeId, int requestId);

    Response acceptRequest(String sessionId, int storeId, int requestId);

    ResponseT<List<EmployeeRequestDTO>> getRequestsByStore(int storeId);
}
