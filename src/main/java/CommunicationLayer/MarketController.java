package CommunicationLayer;
import ServiceLayer.DTOs.*;
import ServiceLayer.MarketManager;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;


@CrossOrigin()
@RestController
@Component
public class MarketController implements IMarketController{

    private MarketManager marketManager;

    public MarketController(){
        this.marketManager = new MarketManager();
    }

    @GetMapping("/login")
    @ResponseBody
    public String login(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        return marketManager.login(username,password).value;
    }

    @GetMapping("/loginSystemManager")
    @ResponseBody
    @Override
    public String loginSystemManager(@RequestParam(value = "username", defaultValue = "") String username,
                                     @RequestParam(value = "password", defaultValue = "") String password) {
        return this.marketManager.loginSystemManager(username, password).value;
    }
    @GetMapping("/logout")
    @ResponseBody
    @Override
    public boolean logout(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return !this.marketManager.logout(sessionId).getError_occurred();
    }
    @GetMapping("/logoutSystemManager")
    @ResponseBody
    @Override
    public boolean logoutSystemManager(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return !this.marketManager.logoutSystemManager(sessionId).getError_occurred();
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
    public boolean signUpSystemManager(@RequestParam(value = "username", defaultValue = "")  String username,
                                       @RequestParam(value = "password", defaultValue = "")  String password) {
        return !this.marketManager.signUpSystemManager(username, password).getError_occurred();
    }
    @GetMapping("/enterMarket")
    @ResponseBody
    @Override
    public boolean enterMarket() {
        return !this.marketManager.enterMarket().getError_occurred();
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
        return !marketManager.signUp(username,password).getError_occurred();
    }


    @GetMapping("/addProductToCart")
    @ResponseBody
    public boolean addProductToCart(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
            @RequestParam(value = "productId", defaultValue = "-1") int productId,
            @RequestParam(value = "amount", defaultValue = "-1") int amount) {
        return !marketManager.addProductToCart(sessionId,storeId,productId,amount).getError_occurred();
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
    public boolean removeProductFromCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                          @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                          @RequestParam(value = "productId", defaultValue = "-1") int productId) {
        return !marketManager.removeProductFromCart(sessionId, storeId, productId).getError_occurred();
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
    public ProductDTO addProduct(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                 @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                 @RequestParam(value = "productName", defaultValue = "") String productName,
                                 @RequestParam(value = "price", defaultValue = "-1") double price,
                                 @RequestParam(value = "category", defaultValue = "")String category,
                                 @RequestParam(value = "quantity", defaultValue = "-1")int quantity,
                                 @RequestParam(value = "description", defaultValue = "") String description) {
        return this.marketManager.addProduct(sessionId, storeId, productName, price, category, quantity, description).value;
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
        return this.editProductPrice(sessionId, storeId, productId, newPrice);
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
                                         @RequestParam(value = "minQuantity", defaultValue = "-1") int minQuantity,
                                         @RequestParam(value = "allowNone", defaultValue = "false") boolean allowNone) {
        return !this.marketManager.addMaxQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone).getError_occurred();
    }


    @GetMapping("/getProductsByCategory")
    @ResponseBody
    public List<ProductDTO> getProductsByCategory(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String categoryName) {
        return marketManager.getProductsByCategory(sessionId,categoryName).value;
    }

    @GetMapping("/getProductsByName")
    @ResponseBody
    public List<ProductDTO> getProductsByName(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String Name) {
        return marketManager.getProductsByName(sessionId,Name).value;
    }

    @GetMapping("/getProductsBySubstring")
    @ResponseBody
    public List<ProductDTO> getProductsBySubstring(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String subName) {
        return marketManager.getProductsBySubstring(sessionId,subName).value;
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
    public boolean changeProductQuantity(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                         @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                         @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                         @RequestParam(value = "quantity", defaultValue = "-1") int quantity) {
        return !this.marketManager.changeProductQuantity(sessionId, storeId, productId, quantity).getError_occurred();
    }

}