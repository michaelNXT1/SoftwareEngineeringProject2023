package CommunicationLayer;

import ServiceLayer.DTOs.*;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
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


@CrossOrigin()
@RestController
@Component
public class MarketController implements IMarketController {

    private final MarketManager marketManager;
    private static MarketController instance = null;

    public static MarketController getInstance() {
        if (instance == null) {
            instance = new MarketController();
        }
        return instance;
    }

    private MarketController() {
        this.marketManager = new MarketManager();
    }

    @GetMapping("/login")
    @ResponseBody
    public String login(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        return marketManager.login(username, password).value;
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
    public List<StoreDTO> getStores(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                    @RequestParam(value = "storeSubString", defaultValue = "") String storeSubString) {
        return this.marketManager.getStores(sessionId, storeSubString).value;
    }

    @GetMapping("/getStore")
    @ResponseBody
    @Override
    public StoreDTO getStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                             @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return this.marketManager.getStore(sessionId, storeId).value;
    }

    @GetMapping("/getProduct")
    @ResponseBody
    @Override
    public ProductDTO getProduct(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                 @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                 @RequestParam(value = "productId", defaultValue = "-1") int productId) {
        return this.marketManager.getProduct(sessionId, storeId, productId).value;
    }

    @GetMapping("/signUpSystemManager")
    @ResponseBody
    @Override
    public boolean signUpSystemManager(@RequestParam(value = "username", defaultValue = "") String username,
                                       @RequestParam(value = "password", defaultValue = "") String password) {
        return !this.marketManager.signUpSystemManager(username, password).getError_occurred();
    }

    @GetMapping("/enterMarket")
    @ResponseBody
    @Override
    public String enterMarket() {
        return this.marketManager.enterMarket().value;
    }

    @GetMapping("/exitMarket")
    @ResponseBody
    @Override
    public boolean exitMarket(@RequestParam(value = "username", defaultValue = "") String sessionId) {
        return !this.marketManager.exitMarket(sessionId).getError_occurred();
    }

    @GetMapping("/signUp")
    @ResponseBody
    public boolean signUp(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        return !marketManager.signUp(username, password).getError_occurred();
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


    @GetMapping("/editProductInCart")
    @ResponseBody
    public boolean editProductInCart(
            @RequestParam(value = "sessionId", defaultValue = "-1") String sessionId,
            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
            @RequestParam(value = "productId", defaultValue = "-1") int productId,
            @RequestParam(value = "amount", defaultValue = "-1") int amount) {
        return !marketManager.changeProductQuantity(sessionId, storeId, productId, amount).getError_occurred();
    }

    @GetMapping("/removeProductFromCart")
    @ResponseBody
    public Response removeProductFromCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                          @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                          @RequestParam(value = "productId", defaultValue = "-1") int productId) {
        return marketManager.removeProductFromCart(sessionId, storeId, productId);
    }

//    @GetMapping("/clearCart")
//    @ResponseBody
//    public ActionResultDTO clearCart(@RequestParam(value = "sessionId", defaultValue = "-1") int sessionId) {
//        return guestUserHandler.clearCart(sessionId);
//    }

    /*
    @GetMapping("/setPaymentDetails")
    @ResponseBody
    public boolean setPaymentDetails(@RequestParam(value = "sessionId", defaultValue = "") int sessionId,
                                     @RequestParam(value = "paymentDetails", defaultValue = "") String paymentDetails) {
        return guestUserHandler.setPaymentDetails(sessionId, paymentDetails);
    }
    */


    @GetMapping("/purchaseShoppingCart")
    @ResponseBody
    public PurchaseDTO purchaseShoppingCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.purchaseShoppingCart(sessionId).value;
    }

    @GetMapping("/openStore")
    @ResponseBody
    @Override
    public Integer openStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                             @RequestParam(value = "storeName", defaultValue = "") String storeName) {
        return this.marketManager.openStore(sessionId, storeName).value;
    }

    @GetMapping("/getPurchaseHistory")
    @ResponseBody
    @Override
    public List<PurchaseDTO> getPurchaseHistory(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return this.marketManager.getPurchaseHistory(sessionId, storeId).value;
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
        return this.marketManager.addProduct(sessionId, storeId, productName, price, category, quantity, description);
    }

    @GetMapping("/editProductName")
    @ResponseBody
    @Override
    public boolean editProductName(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                   @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                   @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                   @RequestParam(value = "newName", defaultValue = "") String newName) {
        return !this.marketManager.editProductName(sessionId, storeId, productId, newName).getError_occurred();
    }

    @GetMapping("/editProductPrice")
    @ResponseBody
    @Override
    public boolean editProductPrice(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                    @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                    @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                    @RequestParam(value = "newPrice", defaultValue = "-1") int newPrice) {
        return !this.marketManager.editProductPrice(sessionId, storeId, productId, newPrice).getError_occurred();
    }

    @GetMapping("/editProductCategory")
    @ResponseBody
    @Override
    public boolean editProductCategory(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                       @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                       @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                       @RequestParam(value = "newCategory", defaultValue = "") String newCategory) {
        return !this.marketManager.editProductCategory(sessionId, storeId, productId, newCategory).getError_occurred();
    }

    @GetMapping("/removeProductFromStore")
    @ResponseBody
    @Override
    public boolean removeProductFromStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                          @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                          @RequestParam(value = "productId", defaultValue = "-1") int productId) {
        return !this.marketManager.removeProductFromStore(sessionId, storeId, productId).getError_occurred();
    }

    @GetMapping("/setPositionOfMemberToStoreManager")
    @ResponseBody
    @Override
    public boolean setPositionOfMemberToStoreManager(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                     @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                                     @RequestParam(value = "MemberToBecomeManager", defaultValue = "") String MemberToBecomeManager) {
        return !this.marketManager.setPositionOfMemberToStoreManager(sessionId, storeID, MemberToBecomeManager).getError_occurred();
    }

    @GetMapping("/setPositionOfMemberToStoreOwner")
    @ResponseBody
    @Override
    public boolean setPositionOfMemberToStoreOwner(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                   @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                                   @RequestParam(value = "MemberToBecomeOwner", defaultValue = "") String MemberToBecomeOwner) {
        return !this.marketManager.setPositionOfMemberToStoreOwner(sessionId, storeID, MemberToBecomeOwner).getError_occurred();
    }

    @GetMapping("/addStoreManagerPermissions")
    @ResponseBody
    @Override
    public boolean addStoreManagerPermissions(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                              @RequestParam(value = "storeManager", defaultValue = "") String storeManager,
                                              @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                              @RequestParam(value = "newPermission", defaultValue = "-1") int newPermission) {
        return !this.marketManager.addStoreManagerPermissions(sessionId, storeManager, storeID, newPermission).getError_occurred();
    }

    @GetMapping("/removeStoreManagerPermissions")
    @ResponseBody
    @Override
    public boolean removeStoreManagerPermissions(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                 @RequestParam(value = "storeManager", defaultValue = "") String storeManager,
                                                 @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                                 @RequestParam(value = "newPermission", defaultValue = "-1") int newPermission) {
        return !this.marketManager.removeStoreManagerPermissions(sessionId, storeManager, storeID, newPermission).getError_occurred();
    }

    @GetMapping("/getStoreEmployees")
    @ResponseBody
    @Override
    public List<MemberDTO> getStoreEmployees(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                             @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return this.marketManager.getStoreEmployees(sessionId, storeId).value;
    }

    @GetMapping("/closeStore")
    @ResponseBody
    @Override
    public boolean closeStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                              @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return !this.marketManager.closeStore(sessionId, storeId).getError_occurred();
    }

    @GetMapping("/getStoresPurchases")
    @ResponseBody
    @Override
    public Map<StoreDTO, List<PurchaseDTO>> getStoresPurchases(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return this.marketManager.getStoresPurchases(sessionId).value;
    }

    @GetMapping("/addProductTimeRestrictionPolicy")
    @ResponseBody
    @Override
    public boolean addProductTimeRestrictionPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                   @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                                   @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                                   @RequestParam(value = "startTime", defaultValue = "") LocalTime startTime,
                                                   @RequestParam(value = "endTime", defaultValue = "") LocalTime endTime) {
        return !this.marketManager.addProductTimeRestrictionPolicy(sessionId, storeId, productId, startTime, endTime).getError_occurred();
    }

    @GetMapping("/addCategoryTimeRestrictionPolicy")
    @ResponseBody
    @Override
    public boolean addCategoryTimeRestrictionPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                    @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                                    @RequestParam(value = "category", defaultValue = "") String category,
                                                    @RequestParam(value = "startTime", defaultValue = "") LocalTime startTime,
                                                    @RequestParam(value = "endTime", defaultValue = "") LocalTime endTime) {
        return !this.marketManager.addCategoryTimeRestrictionPolicy(sessionId, storeId, category, startTime, endTime).getError_occurred();
    }

    @GetMapping("/joinPolicies")
    @ResponseBody
    @Override
    public boolean joinPolicies(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                @RequestParam(value = "policyId1", defaultValue = "-1") int policyId1,
                                @RequestParam(value = "policyId2", defaultValue = "-1") int policyId2,
                                @RequestParam(value = "operator", defaultValue = "0") int operator) {
        return !this.marketManager.joinPolicies(sessionId, storeId, policyId1, policyId2, operator).getError_occurred();
    }

    @GetMapping("/removePolicy")
    @ResponseBody
    @Override
    public boolean removePolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                @RequestParam(value = "policyId", defaultValue = "-1") int policyId) {
        return !this.marketManager.removePolicy(sessionId, storeId, policyId).getError_occurred();
    }

    @GetMapping("/addMinQuantityPolicy")
    @ResponseBody
    @Override
    public boolean addMinQuantityPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                        @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                        @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                        @RequestParam(value = "minQuantity", defaultValue = "-1") int minQuantity,
                                        @RequestParam(value = "allowNone", defaultValue = "false") boolean allowNone) {
        return !this.marketManager.addMinQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone).getError_occurred();
    }

    @GetMapping("/addMaxQuantityPolicy")
    @ResponseBody
    @Override
    public boolean addMaxQuantityPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                        @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                        @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                        @RequestParam(value = "minQuantity", defaultValue = "-1") int minQuantity) {
        return !this.marketManager.addMaxQuantityPolicy(sessionId, storeId, productId, minQuantity).getError_occurred();
    }

    @GetMapping("/addProductDiscount")
    @ResponseBody
    @Override
    public Response addProductDiscount(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                       @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                       @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                       @RequestParam(value = "discountPercentage", defaultValue = "-1.0") double discountPercentage,
                                       @RequestParam(value = "compositionType", defaultValue = "-1.0") int compositionType) {
        return this.marketManager.addProductDiscount(sessionId, storeId, productId, discountPercentage, compositionType);
    }

    @GetMapping("/addCategoryDiscount")
    @ResponseBody
    @Override
    public Response addCategoryDiscount(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                        @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                        @RequestParam(value = "category", defaultValue = "") String category,
                                        @RequestParam(value = "discountPercentage", defaultValue = "-1.0") double discountPercentage,
                                        @RequestParam(value = "compositionType", defaultValue = "-1.0") int compositionType) {
        return this.marketManager.addCategoryDiscount(sessionId, storeId, category, discountPercentage, compositionType);
    }

    @GetMapping("/addStoreDiscount")
    @ResponseBody
    @Override
    public Response addStoreDiscount(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                     @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                     @RequestParam(value = "discountPercentage", defaultValue = "-1.0") double discountPercentage,
                                     @RequestParam(value = "compositionType", defaultValue = "-1.0") int compositionType) {
        return this.marketManager.addStoreDiscount(sessionId, storeId, discountPercentage, compositionType);
    }

    @Override
    public Response addMinQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int minQuantity, boolean allowNone) {
        return marketManager.addMinQuantityDiscountPolicy(sessionId, storeId, discountId, productId, minQuantity, allowNone);
    }

    @Override
    public Response addMaxQuantityDiscountPolicy(String sessionId, int storeId, int discountId, int productId, int maxQuantity, boolean allowNone) {
        return null;
    }

    @Override
    public Response addMinBagTotalDiscountPolicy(String sessionId, int storeId, int discountId, double minTotal) {
        return marketManager.addMinBagTotalDiscountPolicy(sessionId, storeId, discountId, minTotal);
    }

    @Override
    public Response joinDiscountPolicies(String sessionId, int storeId, int policyId1, int policyId2, int operator) {
        return null;
    }

    @Override
    public Response removeDiscountPolicy(String sessionId, int storeId, int policyId) {
        return null;
    }


    @GetMapping("/getProductsByCategory")
    @ResponseBody
    public List<ProductDTO> getProductsByCategory(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String categoryName) {
        return marketManager.getProductsByCategory(sessionId, categoryName).value;
    }

    @GetMapping("/getProductsByName")
    @ResponseBody
    public List<ProductDTO> getProductsByName(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String Name) {
        return marketManager.getProductsByName(sessionId, Name).value;
    }

    @GetMapping("/getProductsBySubstring")
    @ResponseBody
    public List<ProductDTO> getProductsBySubstring(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String subName) {
        return marketManager.getProductsBySubstring(sessionId, subName).value;
    }

    @GetMapping("/getSearchResults")
    @ResponseBody
    @Override
    public List<ProductDTO> getSearchResults(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return this.marketManager.getSearchResults(sessionId).value;
    }

    @GetMapping("/filterSearchResultsByCategory")
    @ResponseBody
    @Override
    public List<ProductDTO> filterSearchResultsByCategory(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                          @RequestParam(value = "category", defaultValue = "") String category) {
        return this.marketManager.filterSearchResultsByCategory(sessionId, category).value;
    }

    @GetMapping("/filterSearchResultsByPrice")
    @ResponseBody
    @Override
    public List<ProductDTO> filterSearchResultsByPrice(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                       @RequestParam(value = "minPrice", defaultValue = "-1") double minPrice,
                                                       @RequestParam(value = "maxPrice", defaultValue = "-1") double maxPrice) {
        return this.marketManager.filterSearchResultsByPrice(sessionId, minPrice, maxPrice).value;
    }


    @GetMapping("/getShoppingCart")
    @ResponseBody
    public ShoppingCartDTO getShoppingCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.getShoppingCart(sessionId).value;
    }

    @GetMapping("/changeProductQuantity")
    @ResponseBody
    @Override
    public Response changeProductQuantity(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                          @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                          @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                          @RequestParam(value = "quantity", defaultValue = "-1") int quantity) {
        return this.marketManager.changeProductQuantity(sessionId, storeId, productId, quantity);
    }

    @GetMapping("/getAllCategories")
    @ResponseBody
    @Override
    public List<String> getAllCategories() {
        return this.marketManager.getAllCategories().value;
    }

    @GetMapping("/addPaymentMethod")
    @ResponseBody
    @Override
    public boolean addPaymentMethod(String sessionId, String cardNumber, String month, String year, String cvv) {
        return !this.marketManager.addPaymentMethod(sessionId, cardNumber, month, year, cvv).getError_occurred();
    }

    @GetMapping("/getSearchKeyword")
    @ResponseBody
    @Override
    public String getSearchKeyword(String sessionId) {
        return this.marketManager.getSearchKeyword(sessionId).value;
    }

    @GetMapping("/getUsername")
    @ResponseBody
    @Override
    public String getUsername(String sessionId) {
        return this.marketManager.getUsername(sessionId).value;
    }

    @GetMapping("/getResponsibleStores")
    @ResponseBody
    @Override
    public List<StoreDTO> getResponsibleStores(String sessionId) {
        return this.marketManager.getResponsibleStores(sessionId).value;
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


}
