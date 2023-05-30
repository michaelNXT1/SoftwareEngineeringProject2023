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
    Response
    signUpSystemManager(String username, String password);

    ResponseT<String>
    enterMarket();

    Response
    exitMarket(String sessionId);

    //User methods
    Response
    signUp(String username, String password);

    public ResponseT<String>
    login(String username, String password, NotificationBroker notificationBroker);

    Response
    logout(String sessionId);

    ResponseT<Boolean>
    isLoggedIn(String sessionId);

    //Interaction methods
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

    ResponseT<Map<ProductDTO, Integer>>
    getProductsByStore(int storeId);

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

    Response
    addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv);

    //Management methods
    ResponseT<Integer>
    openStore(String sessionId, String storeName);

    Response
    closeStore(String sessionId, int storeId);

    ResponseT<List<PurchaseDTO>>
    getPurchaseHistory(String sessionId, int storeId);

    ResponseT<List<StoreDTO>>
    getResponsibleStores(String sessionId);

    //Product management methods
    ResponseT<ProductDTO>
    addProduct(String sessionId, int storeId, String productName, double price, String category, int quantity, String description);

    Response
    editProductName(String sessionId, int storeId, int productId, String newName);

    Response
    editProductPrice(String sessionId, int storeId, int productId, double newPrice);

    Response
    editProductCategory(String sessionId, int storeId, int productId, String newCategory);

    Response
    removeProductFromStore(String sessionId, int storeId, int productId);

    //Employee management methods
    Response
    setPositionOfMemberToStoreManager(String sessionId, int storeID, String MemberToBecomeManager);

    Response
    setPositionOfMemberToStoreOwner(String sessionId, int storeID, String MemberToBecomeOwner);

    Response
    setStoreManagerPermissions(String sessionId, int storeId, String storeManager, Set<PositionDTO.permissionType> permissions);

    Response
    addStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);

    Response
    removeStoreManagerPermissions(String sessionId, String storeManager, int storeID, int newPermission);

    ResponseT<Boolean>
    hasPermission(String sessionId, int storeId, PositionDTO.permissionType employeeList);

    Response
    removeStoreOwner(String sessionId, int storeId, String storeOwnerToRemove);
    ResponseT<List<MemberDTO>>
    getStoreEmployees(String sessionId, int storeId);

    //Purchase policy methods
    Response
    addMaxQuantityPurchasePolicy(String sessionId, int storeId, int productId, int maxQuantity);

    Response
    addMinQuantityPurchasePolicy(String sessionId, int storeId, int productId, int minQuantity, boolean allowNone);

    Response
    addProductTimeRestrictionPurchasePolicy(String sessionId, int storeId, int productId, LocalTime startTime, LocalTime endTime);

    Response
    addCategoryTimeRestrictionPurchasePolicy(String sessionId, int storeId, String category, LocalTime startTime, LocalTime endTime);

    Response
    joinPurchasePolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator);

    Response
    removePurchasePolicy(String sessionId, int storeId, int policyId);

    ResponseT<List<BasePurchasePolicyDTO>>
    getPurchasePoliciesByStoreId(int storeId);

    ResponseT<List<String>>
    getPurchasePolicyTypes();

    //Discount methods
    Response
    addProductDiscount(String sessionId, int storeId, int productId, double discountPercentage, int compositionType);

    Response
    addCategoryDiscount(String sessionId, int storeId, String category, double discountPercentage, int compositionType);

    Response
    addStoreDiscount(String sessionId, int storeId, double discountPercentage, int compositionType);

    Response
    removeDiscount(String sessionId, int storeId, int discountId);

    ResponseT<Map<DiscountDTO, List<BaseDiscountPolicyDTO>>>
    getDiscountPolicyMap(int storeId);

    //Discount policy methods
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
    getDiscountPolicyTypes();

    //System manager methods
    ResponseT<Map<StoreDTO, List<PurchaseDTO>>>
    getStoresPurchases(String sessionId);

    ResponseT<List<MemberDTO>>
    getInformationAboutMembers(String sessionId);

    Response
    removeMember(String sessionId, String memberName);

    //Other
    ResponseT<List<String>>
    getAllCategories();

    ResponseT<String>
    getUsername(String sessionId);

    ResponseT<String>
    getSearchKeyword(String sessionId);

    ResponseT<Set<PositionDTO.permissionType>> getPermissions(String sessionId, int storeId, String username);


//        Response
//        logoutSystemManager(String sessionId);

//        ResponseT<String>
//        loginSystemManager(String username, String password);
}
