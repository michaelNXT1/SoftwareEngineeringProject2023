package CommunicationLayer;

import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.*;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.MarketManager;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;


@CrossOrigin()
@RestController
@Component
@RequestMapping("/market")
public class MarketController implements IMarketController {

    private static MarketController instance = null;
    private final MarketManager marketManager;
    private final NotificationController notificationBroker;

    private MarketController() {
        this.marketManager = new MarketManager("initFile2",false);
        this.notificationBroker = new NotificationController();
    }


    public static MarketController getInstance() {
        if (instance == null) {
            instance = new MarketController();
        }
        return instance;
    }

    @PostMapping("/signUpSystemManager")
    @ResponseBody
    @Override
    public Response signUpSystemManager(@RequestParam(value = "username", defaultValue = "") String username,
                                        @RequestParam(value = "password", defaultValue = "") String password) {
        return this.marketManager.signUpSystemManager(username, password);
    }

    @PostMapping("/enterMarket")
    @ResponseBody
    @Override
    public ResponseT<String> enterMarket() {
        return this.marketManager.enterMarket();
    }

    @GetMapping("/exitMarket")
    @ResponseBody
    @Override
    public Response exitMarket(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return this.marketManager.exitMarket(sessionId);
    }
    @GetMapping("/removeMember")
    @ResponseBody
    @Override
    public Response removeMember(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                 @RequestParam(value = "memberName", defaultValue = "") String memberName) {
        return marketManager.removeMember(sessionId, memberName);
    }

    @GetMapping("/signUp")
    @ResponseBody
    @Override
    public Response signUp(@RequestParam(value = "username", defaultValue = "") String username,
                           @RequestParam(value = "password", defaultValue = "") String password) {
        return this.marketManager.signUp(username, password);
    }

    @GetMapping("/login")
    @ResponseBody
    public ResponseT<String> login(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        return marketManager.login(username, password, notificationBroker);
    }
    @GetMapping("/loginSystemManager")
    @ResponseBody
    public ResponseT<String> loginSystemManager(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        return marketManager.loginSystemManager(username, password, notificationBroker);
    }


    @GetMapping("/logout")
    @ResponseBody
    @Override
    public ResponseT<String> logout(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.logout(sessionId);
    }

    @GetMapping("/getStores")
    @ResponseBody
    @Override
    public ResponseT<List<StoreDTO>> getStores(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                               @RequestParam(value = "storeSubString", defaultValue = "") String storeSubString) {
        return marketManager.getStores(sessionId, storeSubString);
    }

    @GetMapping("/getStore")
    @ResponseBody
    @Override
    public ResponseT<StoreDTO> getStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                        @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return marketManager.getStore(sessionId, storeId);
    }

    @GetMapping("/getProduct")
    @ResponseBody
    @Override
    public ResponseT<ProductDTO> getProduct(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                            @RequestParam(value = "productId", defaultValue = "-1") int productId) {
        return marketManager.getProduct(sessionId, storeId, productId);
    }

    @GetMapping("/getProductsByName")
    @ResponseBody
    public ResponseT<List<ProductDTO>> getProductsByName(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String Name) {
        return marketManager.getProductsByName(sessionId, Name);
    }

    @GetMapping("/getProductsByCategory")
    @ResponseBody
    public ResponseT<List<ProductDTO>> getProductsByCategory(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String categoryName) {
        return marketManager.getProductsByCategory(sessionId, categoryName);
    }

    @GetMapping("/getProductsBySubstring")
    @ResponseBody
    public ResponseT<List<ProductDTO>> getProductsBySubstring(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String subName) {
        return marketManager.getProductsBySubstring(sessionId, subName);
    }

//    @GetMapping("/clearCart")
//    @ResponseBody
//    public ActionResultDTO clearCart(@RequestParam(value = "sessionId", defaultValue = "-1") int sessionId) {
//        return guestUserHandler.clearCart(sessionId);
//    }

    /*
    @GetMapping("/setPaymentDetails")
    @ResponseBody
    public Response setPaymentDetails(@RequestParam(value = "sessionId", defaultValue = "") int sessionId,
                                     @RequestParam(value = "paymentDetails", defaultValue = "") String paymentDetails) {
        return guestUserHandler.setPaymentDetails(sessionId, paymentDetails);
    }
    */

    @GetMapping("/getSearchResults")
    @ResponseBody
    @Override
    public ResponseT<List<ProductDTO>> getSearchResults(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.getSearchResults(sessionId);
    }

    @GetMapping("/filterSearchResultsByCategory")
    @ResponseBody
    @Override
    public ResponseT<List<ProductDTO>> filterSearchResultsByCategory(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                                     @RequestParam(value = "category", defaultValue = "") String category) {
        return marketManager.filterSearchResultsByCategory(sessionId, category);
    }

    @GetMapping("/filterSearchResultsByPrice")
    @ResponseBody
    @Override
    public ResponseT<List<ProductDTO>> filterSearchResultsByPrice(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                                  @RequestParam(value = "minPrice", defaultValue = "-1") double minPrice,
                                                                  @RequestParam(value = "maxPrice", defaultValue = "-1") double maxPrice) {
        return marketManager.filterSearchResultsByPrice(sessionId, minPrice, maxPrice);
    }


    @GetMapping("/addProductToCart")
    @ResponseBody
    public Response addProductToCart(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
            @RequestParam(value = "productId", defaultValue = "-1") int productId,
            @RequestParam(value = "amount", defaultValue = "-1") int amount) {
        return marketManager.addProductToCart(sessionId, storeId, productId, amount);
    }

    @GetMapping("/getShoppingCart")
    @ResponseBody
    public ResponseT<ShoppingCartDTO> getShoppingCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.getShoppingCart(sessionId);
    }

    @GetMapping("/changeProductQuantity")
    @ResponseBody
    @Override
    public Response changeProductQuantity(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                          @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                          @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                          @RequestParam(value = "quantity", defaultValue = "-1") int quantity) {
        return marketManager.changeProductQuantity(sessionId, storeId, productId, quantity);
    }

    @GetMapping("/removeProductFromCart")
    @ResponseBody
    public Response removeProductFromCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                          @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                          @RequestParam(value = "productId", defaultValue = "-1") int productId) {
        return marketManager.removeProductFromCart(sessionId, storeId, productId);
    }

    @GetMapping("/purchaseShoppingCart")
    @ResponseBody
    public ResponseT<PurchaseDTO> purchaseShoppingCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.purchaseShoppingCart(sessionId);
    }

    @GetMapping("/openStore")
    @ResponseBody
    @Override
    public ResponseT<Integer> openStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                        @RequestParam(value = "storeName", defaultValue = "") String storeName) {
        return marketManager.openStore(sessionId, storeName);
    }

    @GetMapping("/getPurchaseHistory")
    @ResponseBody
    @Override
    public ResponseT<List<PurchaseDTO>> getPurchaseHistory(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                           @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return marketManager.getPurchaseHistory(sessionId, storeId);
    }

    @GetMapping("/addProduct")
    @ResponseBody
    @Override
    public ResponseT<ProductDTO> addProduct(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                            @RequestParam(value = "productName", defaultValue = "") String productName,
                                            @RequestParam(value = "price", defaultValue = "-1") double price,
                                            @RequestParam(value = "category", defaultValue = "") String category,
                                            @RequestParam(value = "quantity", defaultValue = "-1") int quantity,
                                            @RequestParam(value = "description", defaultValue = "") String description) {
        return marketManager.addProduct(sessionId, storeId, productName, price, category, quantity, description);
    }

    @GetMapping("/editProductName")
    @ResponseBody
    @Override
    public Response editProductName(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                    @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                    @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                    @RequestParam(value = "newName", defaultValue = "") String newName) {
        return marketManager.editProductName(sessionId, storeId, productId, newName);
    }

    @GetMapping("/editProductPrice")
    @ResponseBody
    @Override
    public Response editProductPrice(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                     @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                     @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                     @RequestParam(value = "newPrice", defaultValue = "-1") double newPrice) {
        return marketManager.editProductPrice(sessionId, storeId, productId, newPrice);
    }

    @GetMapping("/editProductCategory")
    @ResponseBody
    @Override
    public Response editProductCategory(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                        @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                        @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                        @RequestParam(value = "newCategory", defaultValue = "") String newCategory) {
        return marketManager.editProductCategory(sessionId, storeId, productId, newCategory);
    }

    @GetMapping("/removeProductFromStore")
    @ResponseBody
    @Override
    public Response removeProductFromStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                           @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                           @RequestParam(value = "productId", defaultValue = "-1") int productId) {
        return marketManager.removeProductFromStore(sessionId, storeId, productId);
    }

    @GetMapping("/setPositionOfMemberToStoreManager")
    @ResponseBody
    @Override
    public Response setPositionOfMemberToStoreManager(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                      @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                                      @RequestParam(value = "MemberToBecomeManager", defaultValue = "") String MemberToBecomeManager) {
        return marketManager.setPositionOfMemberToStoreManager(sessionId, storeID, MemberToBecomeManager);
    }

    @GetMapping("/setPositionOfMemberToStoreOwner")
    @ResponseBody
    @Override
    public Response setPositionOfMemberToStoreOwner(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                    @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                                    @RequestParam(value = "MemberToBecomeOwner", defaultValue = "") String MemberToBecomeOwner) {
        return marketManager.setPositionOfMemberToStoreOwner(sessionId, storeID, MemberToBecomeOwner);
    }

    @GetMapping("/addStoreManagerPermissions")
    @ResponseBody
    @Override
    public Response addStoreManagerPermissions(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                               @RequestParam(value = "storeManager", defaultValue = "") String storeManager,
                                               @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                               @RequestParam(value = "newPermission", defaultValue = "-1") int newPermission) {
        return marketManager.addStoreManagerPermissions(sessionId, storeManager, storeID, newPermission);
    }

    @GetMapping("/removeStoreManagerPermissions")
    @ResponseBody
    @Override
    public Response removeStoreManagerPermissions(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                  @RequestParam(value = "storeManager", defaultValue = "") String storeManager,
                                                  @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                                  @RequestParam(value = "newPermission", defaultValue = "-1") int newPermission) {
        return marketManager.removeStoreManagerPermissions(sessionId, storeManager, storeID, newPermission);
    }

    @GetMapping("/getStoreEmployees")
    @ResponseBody
    @Override
    public ResponseT<List<MemberDTO>> getStoreEmployees(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                        @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return marketManager.getStoreEmployees(sessionId, storeId);
    }

    @GetMapping("/closeStore")
    @ResponseBody
    @Override
    public Response closeStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                               @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return marketManager.closeStore(sessionId, storeId);
    }

    @GetMapping("/getStoresPurchases")
    @ResponseBody
    @Override
    public ResponseT<Map<StoreDTO, List<PurchaseDTO>>> getStoresPurchases(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.getStoresPurchases(sessionId);
    }

    @GetMapping("/addProductTimeRestrictionPolicy")
    @ResponseBody
    @Override
    public Response addProductTimeRestrictionPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                    @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                                    @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                                    @RequestParam(value = "startTime", defaultValue = "") LocalTime startTime,
                                                    @RequestParam(value = "endTime", defaultValue = "") LocalTime endTime) {
        return marketManager.addProductTimeRestrictionPurchasePolicy(sessionId, storeId, productId, startTime, endTime);
    }

    @GetMapping("/addCategoryTimeRestrictionPolicy")
    @ResponseBody
    @Override
    public Response addCategoryTimeRestrictionPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                     @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                                     @RequestParam(value = "category", defaultValue = "") String category,
                                                     @RequestParam(value = "startTime", defaultValue = "") LocalTime startTime,
                                                     @RequestParam(value = "endTime", defaultValue = "") LocalTime endTime) {
        return marketManager.addCategoryTimeRestrictionPurchasePolicy(sessionId, storeId, category, startTime, endTime);
    }

    @GetMapping("/joinPolicies")
    @ResponseBody
    @Override
    public Response joinPolicies(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                 @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                 @RequestParam(value = "policyId1", defaultValue = "-1") int policyId1,
                                 @RequestParam(value = "policyId2", defaultValue = "-1") int policyId2,
                                 @RequestParam(value = "operator", defaultValue = "0") int operator) {
        return marketManager.joinPurchasePolicies(sessionId, storeId, policyId1, policyId2, operator);
    }

    @GetMapping("/removePolicy")
    @ResponseBody
    @Override
    public Response removePolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                 @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                 @RequestParam(value = "policyId", defaultValue = "-1") int policyId) {
        return marketManager.removePurchasePolicy(sessionId, storeId, policyId);
    }

    @GetMapping("/addMinQuantityPolicy")
    @ResponseBody
    @Override
    public Response addMinQuantityPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                         @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                         @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                         @RequestParam(value = "minQuantity", defaultValue = "-1") int minQuantity,
                                         @RequestParam(value = "allowNone", defaultValue = "false") boolean allowNone) {
        return marketManager.addMinQuantityPurchasePolicy(sessionId, storeId, productId, minQuantity, allowNone);
    }

    @GetMapping("/addMaxQuantityPolicy")
    @ResponseBody
    @Override
    public Response addMaxQuantityPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                         @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                         @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                         @RequestParam(value = "minQuantity", defaultValue = "-1") int minQuantity) {
        return marketManager.addMaxQuantityPurchasePolicy(sessionId, storeId, productId, minQuantity);
    }

    @GetMapping("/addProductDiscount")
    @ResponseBody
    @Override
    public ResponseT<Long> addProductDiscount(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                              @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                              @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                              @RequestParam(value = "discountPercentage", defaultValue = "-1.0") double discountPercentage,
                                              @RequestParam(value = "compositionType", defaultValue = "-1.0") int compositionType) {
        return marketManager.addProductDiscount(sessionId, storeId, productId, discountPercentage, compositionType);
    }

    @GetMapping("/addCategoryDiscount")
    @ResponseBody
    @Override
    public ResponseT<Long> addCategoryDiscount(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                               @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                               @RequestParam(value = "category", defaultValue = "") String category,
                                               @RequestParam(value = "discountPercentage", defaultValue = "-1.0") double discountPercentage,
                                               @RequestParam(value = "compositionType", defaultValue = "-1.0") int compositionType) {
        return marketManager.addCategoryDiscount(sessionId, storeId, category, discountPercentage, compositionType);
    }

    @GetMapping("/getInformationAboutMembers")
    @ResponseBody
    @Override
    public ResponseT<List<MemberDTO>> getInformationAboutMembers(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.getInformationAboutMembers(sessionId);
    }

    @GetMapping("/addStoreDiscount")
    @ResponseBody
    @Override
    public ResponseT<Long> addStoreDiscount(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                            @RequestParam(value = "discountPercentage", defaultValue = "-1.0") double discountPercentage,
                                            @RequestParam(value = "compositionType", defaultValue = "-1.0") int compositionType) {
        return marketManager.addStoreDiscount(sessionId, storeId, discountPercentage, compositionType);
    }

    @Override
    public Response addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) {
        return marketManager.addMinQuantityDiscountPolicy(sessionId, storeId, discountId, productId, minQuantity, allowNone);
    }

    @Override
    public Response addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity) {
        return marketManager.addMaxQuantityDiscountPolicy(sessionId, storeId, discountId, productId, maxQuantity);
    }

    @Override
    public Response addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) {
        return marketManager.addMinBagTotalDiscountPolicy(sessionId, storeId, discountId, minTotal);
    }

    @Override
    public Response joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        return marketManager.joinDiscountPolicies(sessionId, storeId, policyId1, policyId2, operator);
    }

    @Override
    public Response removeDiscountPolicy(String sessionId, int storeId, int policyId) {
        return marketManager.removeDiscountPolicy(sessionId, storeId, policyId);
    }

    @GetMapping("/getAllCategories")
    @ResponseBody
    @Override
    public ResponseT<List<String>> getAllCategories() {
        return marketManager.getAllCategories();
    }

    @GetMapping("/addPaymentMethod")
    @ResponseBody
    @Override
    public Response addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv) {
        return marketManager.addPaymentMethod(sessionId, cardNumber, month, year, cvv);
    }

    @GetMapping("/getSearchKeyword")
    @ResponseBody
    @Override
    public ResponseT<String> getSearchKeyword(String sessionId) {
        return marketManager.getSearchKeyword(sessionId);
    }

    @GetMapping("/getUsername")
    @ResponseBody
    @Override
    public ResponseT<String> getUsername(String sessionId) {
        return marketManager.getUsername(sessionId);
    }

    @GetMapping("/getResponsibleStores")
    @ResponseBody
    @Override
    public ResponseT<List<StoreDTO>> getResponsibleStores(String sessionId) {
        return marketManager.getResponsibleStores(sessionId);
    }

    @GetMapping("/isLoggedIn")
    @ResponseBody
    @Override
    public ResponseT<Boolean> isLoggedIn(String sessionId) {
        return marketManager.isLoggedIn(sessionId);
    }

    @Override
    public ResponseT<Map<ProductDTO, Integer>> getProductsByStore(int storeId) {
        return marketManager.getProductsByStore(storeId);
    }

    @Override
    public ResponseT<Map<DiscountDTO, List<BaseDiscountPolicyDTO>>> getDiscountPolicyMap(int storeId) {
        return marketManager.getDiscountPolicyMap(storeId);
    }

    @Override
    public ResponseT<List<BasePurchasePolicyDTO>> getPurchasePoliciesByStoreId(int storeId) {
        return marketManager.getPurchasePoliciesByStoreId(storeId);
    }

    @Override
    public ResponseT<List<String>> getPurchasePolicyTypes() {
        return marketManager.getPurchasePolicyTypes();
    }

    @Override
    public Response removeDiscount(String sessionId, int storeId, int discountId) {
        return marketManager.removeDiscount(sessionId, storeId, discountId);
    }

    @Override
    public ResponseT<List<String>> getDiscountPolicyTypes() {
        return marketManager.getDiscountPolicyTypes();
    }

    @Override
    public ResponseT<Boolean> hasPermission(String sessionId, int storeId, PositionDTO.permissionType employeeList) {
        return marketManager.hasPermission(sessionId, storeId, employeeList);
    }

    @Override
    public Response removeStoreOwner(String sessionId, int storeId, String username) {
        return marketManager.removeStoreOwner(sessionId, storeId, username);
    }

    @Override
    public Response setStoreManagerPermissions(String sessionId, int storeId, String username, Set<PositionDTO.permissionType> mapPermissions) {
        return marketManager.setStoreManagerPermissions(sessionId, storeId, username, mapPermissions);
    }

    @Override
    public ResponseT<Set<PositionDTO.permissionType>> getPermissions(String sessionId, int storeId, String username) {
        return marketManager.getPermissions(sessionId, storeId, username);
    }

    @Override
    public ResponseT<Boolean> hasPaymentMethod(String sessionId) {
        return marketManager.hasPaymentMethod(sessionId);
    }

    @Override
    public ResponseT<Boolean> hasDeliveryAddress(String sessionId) {
        return marketManager.hasDeliveryAddress(sessionId);
    }

    @Override
    public ResponseT<Double> getProductDiscountPercentageInCart(String sessionId, int storeId, int productId) {
        return marketManager.getProductDiscountPercentageInCart(sessionId, storeId, productId);
    }

    @Override
    public Response addSupplyDetails(String sessionId, String name, String address, String city, String country, String zip) {
        return marketManager.addSupplyDetails(sessionId, name, address, city, country, zip);
    }

    @Override
    public ResponseT<List<PurchaseDTO>> getUserPurchaseHistory(String sessionId) {
        return marketManager.getUserPurchaseHistory(sessionId);
    }

    @Override
    public Response makeOffer(String sessionId, int storeId, int productId, Double pricePerItem, Integer quantity) {
        return marketManager.getOffer(sessionId, storeId, productId, pricePerItem, quantity);
    }

    @GetMapping("/editProductInCart")
    @ResponseBody
    public Response editProductInCart(
            @RequestParam(value = "sessionId", defaultValue = "-1") String sessionId,
            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
            @RequestParam(value = "productId", defaultValue = "-1") int productId,
            @RequestParam(value = "amount", defaultValue = "-1") int amount) {
        return marketManager.changeProductQuantity(sessionId, storeId, productId, amount);
    }


}
