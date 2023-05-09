package CommunicationLayer;
import BusinessLayer.Policies.PurchasePolicyExpression;
import BusinessLayer.Product;
import BusinessLayer.Purchase;
import BusinessLayer.ShoppingCart;
import BusinessLayer.Store;
import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.StoreDTO;
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
public class MarketController implements IMarketController{

    private MarketManager marketManager;

    public MarketController(){
        this.marketManager = new MarketManager();
    }

    @GetMapping("/login")
    @ResponseBody
    public ResponseT<String> login(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        return marketManager.login(username,password);
    }

    @GetMapping("/loginSystemManager")
    @ResponseBody
    @Override
    public ResponseT<String> loginSystemManager(@RequestParam(value = "username", defaultValue = "") String username,
                                                @RequestParam(value = "password", defaultValue = "") String password) {
        return this.marketManager.loginSystemManager(username, password);
    }
    @GetMapping("/logout")
    @ResponseBody
    @Override
    public Response logout(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return this.marketManager.logout(sessionId);
    }
    @GetMapping("/logoutSystemManager")
    @ResponseBody
    @Override
    public Response logoutSystemManager(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return this.marketManager.logoutSystemManager(sessionId);
    }
    @GetMapping("/getStores")
    @ResponseBody
    @Override
    public ResponseT<List<Store>> getStores(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                            @RequestParam(value = "storeSubString", defaultValue = "") String storeSubString) {
        return this.marketManager.getStores(sessionId, storeSubString);
    }
    @GetMapping("/getStore")
    @ResponseBody
    @Override
    public ResponseT<Store> getStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                     @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return this.marketManager.getStore(sessionId, storeId);
    }
    @GetMapping("/getProduct")
    @ResponseBody
    @Override
    public ResponseT<ProductDTO> getProduct(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                            @RequestParam(value = "productId", defaultValue = "-1") int productId) {
        return this.marketManager.getProduct(sessionId, storeId, productId);
    }
    @GetMapping("/signUpSystemManager")
    @ResponseBody
    @Override
    public Response signUpSystemManager(@RequestParam(value = "username", defaultValue = "")  String username,
                                        @RequestParam(value = "password", defaultValue = "")  String password) {
        return this.marketManager.signUpSystemManager(username, password);
    }
    @GetMapping("/enterMarket")
    @ResponseBody
    @Override
    public Response enterMarket() {
        return this.marketManager.enterMarket();
    }
    @GetMapping("/exitMarket")
    @ResponseBody
    @Override
    public Response exitMarket(@RequestParam(value = "username", defaultValue = "") String sessionId) {
        return this.marketManager.exitMarket(sessionId);
    }

    @GetMapping("/signUp")
    @ResponseBody
    public Response signUp(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        return marketManager.signUp(username,password);
    }


    @GetMapping("/addProductToCart")
    @ResponseBody
    public Response addProductToCart(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
            @RequestParam(value = "productId", defaultValue = "-1") int productId,
            @RequestParam(value = "amount", defaultValue = "-1") int amount) {
        return marketManager.addProductToCart(sessionId,storeId,productId,amount);
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
    public ResponseT<Purchase> purchaseShoppingCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.purchaseShoppingCart(sessionId);
    }
    @GetMapping("/openStore")
    @ResponseBody
    @Override
    public ResponseT<Integer> openStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                        @RequestParam(value = "storeName", defaultValue = "") String storeName) {
        return this.marketManager.openStore(sessionId, storeName);
    }
    @GetMapping("/getPurchaseHistory")
    @ResponseBody
    @Override
    public ResponseT<List<PurchaseDTO>> getPurchaseHistory(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                           @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return this.marketManager.getPurchaseHistory(sessionId, storeId);
    }
    @GetMapping("/addProduct")
    @ResponseBody
    @Override
    public ResponseT<ProductDTO> addProduct(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                            @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                            @RequestParam(value = "productName", defaultValue = "") String productName,
                                            @RequestParam(value = "price", defaultValue = "-1") double price,
                                            @RequestParam(value = "category", defaultValue = "")String category,
                                            @RequestParam(value = "quantity", defaultValue = "-1")int quantity,
                                            @RequestParam(value = "description", defaultValue = "") String description) {
        return this.marketManager.addProduct(sessionId, storeId, productName, price, category, quantity, description);
    }
    @GetMapping("/editProductName")
    @ResponseBody
    @Override
    public Response editProductName(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                    @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                    @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                    @RequestParam(value = "newName", defaultValue = "") String newName) {
        return this.marketManager.editProductName(sessionId, storeId, productId, newName);
    }
    @GetMapping("/editProductPrice")
    @ResponseBody
    @Override
    public Response editProductPrice(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                     @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                     @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                     @RequestParam(value = "newPrice", defaultValue = "-1") int newPrice) {
        return this.editProductPrice(sessionId, storeId, productId, newPrice);
    }
    @GetMapping("/editProductCategory")
    @ResponseBody
    @Override
    public Response editProductCategory(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                        @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                        @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                        @RequestParam(value = "newCategory", defaultValue = "") String newCategory) {
        return this.marketManager.editProductCategory(sessionId, storeId, productId, newCategory);
    }
    @GetMapping("/removeProductFromStore")
    @ResponseBody
    @Override
    public Response removeProductFromStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                           @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                           @RequestParam(value = "productId", defaultValue = "-1") int productId) {
        return this.marketManager.removeProductFromStore(sessionId, storeId, productId);
    }
    @GetMapping("/setPositionOfMemberToStoreManager")
    @ResponseBody
    @Override
    public Response setPositionOfMemberToStoreManager(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                      @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                                      @RequestParam(value = "MemberToBecomeManager", defaultValue = "") String MemberToBecomeManager) {
        return this.setPositionOfMemberToStoreManager(sessionId, storeID, MemberToBecomeManager);
    }
    @GetMapping("/setPositionOfMemberToStoreOwner")
    @ResponseBody
    @Override
    public Response setPositionOfMemberToStoreOwner(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                    @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                                    @RequestParam(value = "MemberToBecomeOwner", defaultValue = "") String MemberToBecomeOwner) {
        return this.marketManager.setPositionOfMemberToStoreOwner(sessionId, storeID, MemberToBecomeOwner);
    }
    @GetMapping("/addStoreManagerPermissions")
    @ResponseBody
    @Override
    public Response addStoreManagerPermissions(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                               @RequestParam(value = "storeManager", defaultValue = "") String storeManager,
                                               @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                               @RequestParam(value = "newPermission", defaultValue = "-1") int newPermission) {
        return this.marketManager.addStoreManagerPermissions(sessionId, storeManager, storeID, newPermission);
    }
    @GetMapping("/removeStoreManagerPermissions")
    @ResponseBody
    @Override
    public Response removeStoreManagerPermissions(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                  @RequestParam(value = "storeManager", defaultValue = "") String storeManager,
                                                  @RequestParam(value = "storeID", defaultValue = "-1") int storeID,
                                                  @RequestParam(value = "newPermission", defaultValue = "-1") int newPermission) {
        return this.marketManager.removeStoreManagerPermissions(sessionId, storeManager, storeID, newPermission);
    }
    @GetMapping("/getStoreEmployees")
    @ResponseBody
    @Override
    public ResponseT<List<MemberDTO>> getStoreEmployees(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                        @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return this.marketManager.getStoreEmployees(sessionId, storeId);
    }
    @GetMapping("/closeStore")
    @ResponseBody
    @Override
    public Response closeStore(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                               @RequestParam(value = "storeId", defaultValue = "-1") int storeId) {
        return this.marketManager.closeStore(sessionId, storeId);
    }
    @GetMapping("/getStoresPurchases")
    @ResponseBody
    @Override
    public ResponseT<Map<StoreDTO, List<PurchaseDTO>>> getStoresPurchases(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return this.marketManager.getStoresPurchases(sessionId);
    }
    @GetMapping("/addProductTimeRestrictionPolicy")
    @ResponseBody
    @Override
    public Response addProductTimeRestrictionPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                    @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                                    @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                                    @RequestParam(value = "startTime", defaultValue = "") LocalTime startTime,
                                                    @RequestParam(value = "endTime", defaultValue = "") LocalTime endTime) {
        return this.marketManager.addProductTimeRestrictionPolicy(sessionId, storeId, productId, startTime, endTime);
    }
    @GetMapping("/addCategoryTimeRestrictionPolicy")
    @ResponseBody
    @Override
    public Response addCategoryTimeRestrictionPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                     @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                                     @RequestParam(value = "category", defaultValue = "") String category,
                                                     @RequestParam(value = "startTime", defaultValue = "") LocalTime startTime,
                                                     @RequestParam(value = "endTime", defaultValue = "") LocalTime endTime) {
        return this.marketManager.addCategoryTimeRestrictionPolicy(sessionId, storeId, category, startTime, endTime);
    }
    @GetMapping("/joinPolicies")
    @ResponseBody
    @Override
    public Response joinPolicies(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                 @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                 @RequestParam(value = "policyId1", defaultValue = "-1") int policyId1,
                                 @RequestParam(value = "policyId2", defaultValue = "-1") int policyId2,
                                 @RequestParam(value = "operator", defaultValue = "0") PurchasePolicyExpression.JoinOperator operator) {
        return this.marketManager.joinPolicies(sessionId, storeId, policyId1, policyId2, operator);
    }
    @GetMapping("/removePolicy")
    @ResponseBody
    @Override
    public Response removePolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                 @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                 @RequestParam(value = "policyId", defaultValue = "-1") int policyId) {
        return this.marketManager.removePolicy(sessionId, storeId, policyId);
    }
    @GetMapping("/addMinQuantityPolicy")
    @ResponseBody
    @Override
    public Response addMinQuantityPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                         @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                         @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                         @RequestParam(value = "minQuantity", defaultValue = "-1") int minQuantity,
                                         @RequestParam(value = "allowNone", defaultValue = "false") boolean allowNone) {
        return this.marketManager.addMinQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone);
    }
    @GetMapping("/addMaxQuantityPolicy")
    @ResponseBody
    @Override
    public Response addMaxQuantityPolicy(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                         @RequestParam(value = "storeId", defaultValue = "-1") int storeId,
                                         @RequestParam(value = "productId", defaultValue = "-1") int productId,
                                         @RequestParam(value = "minQuantity", defaultValue = "-1") int minQuantity,
                                         @RequestParam(value = "allowNone", defaultValue = "false") boolean allowNone) {
        return this.marketManager.addMaxQuantityPolicy(sessionId, storeId, productId, minQuantity, allowNone);
    }


    @GetMapping("/getProductsByCategory")
    @ResponseBody
    public ResponseT<List<ProductDTO>> getProductsByCategory(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String categoryName) {
        return marketManager.getProductsByCategory(sessionId,categoryName);
    }

    @GetMapping("/getProductsByName")
    @ResponseBody
    public ResponseT<List<Product>> getProductsByName(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String Name) {
        return marketManager.getProductsByName(sessionId,Name);
    }

    @GetMapping("/getProductsBySubstring")
    @ResponseBody
    public ResponseT<List<ProductDTO>> getProductsBySubstring(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String subName) {
        return marketManager.getProductsBySubstring(sessionId,subName);
    }
    @GetMapping("/getSearchResults")
    @ResponseBody
    @Override
    public ResponseT<List<ProductDTO>> getSearchResults(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return this.marketManager.getSearchResults(sessionId);
    }
    @GetMapping("/filterSearchResultsByCategory")
    @ResponseBody
    @Override
    public ResponseT<List<ProductDTO>> filterSearchResultsByCategory(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                                     @RequestParam(value = "category", defaultValue = "") String category) {
        return this.marketManager.filterSearchResultsByCategory(sessionId, category);
    }
    @GetMapping("/filterSearchResultsByPrice")
    @ResponseBody
    @Override
    public ResponseT<List<ProductDTO>> filterSearchResultsByPrice(@RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                                                  @RequestParam(value = "minPrice", defaultValue = "-1") double minPrice,
                                                                  @RequestParam(value = "maxPrice", defaultValue = "-1") double maxPrice) {
        return this.marketManager.filterSearchResultsByPrice(sessionId, minPrice, maxPrice);
    }


    @GetMapping("/getShoppingCart")
    @ResponseBody
    public ResponseT<ShoppingCart> getShoppingCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.getShoppingCart(sessionId);
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

}
