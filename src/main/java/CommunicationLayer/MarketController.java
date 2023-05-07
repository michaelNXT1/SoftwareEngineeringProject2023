package CommunicationLayer;
import ServiceLayer.MarketManager;
import ServiceLayer.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@CrossOrigin()
@RestController
@Component
public class MarketController {

    private MarketManager marketManager;

    public MarketController(){
        this.marketManager = new MarketManager();
    }

    @GetMapping("/login")
    @ResponseBody
    public Response login(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {
        return marketManager.login(username,password);
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
    public Response purchaseShoppingCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.purchaseShoppingCart(sessionId);
    }

//    @GetMapping("/confirmPurchase")
//    @ResponseBody
//    public ActionResultDTO confirmPurchase(@RequestParam(value = "sessionId", defaultValue = "-1") int sessionId,
//                                           @RequestParam(value = "cardNumber", defaultValue = "") String cardNumber,
//                                           @RequestParam(value = "cardMonth", defaultValue = "") String cardMonth,
//                                           @RequestParam(value = "cardYear", defaultValue = "") String cardYear,
//                                           @RequestParam(value = "cardHolder", defaultValue = "") String cardHolder,
//                                           @RequestParam(value = "cardCcv", defaultValue = "") String cardCcv,
//                                           @RequestParam(value = "cardId", defaultValue = "") String cardId,
//                                           @RequestParam(value = "buyerName", defaultValue = "") String buyerName,
//                                           @RequestParam(value = "address", defaultValue = "") String address,
//                                           @RequestParam(value = "city", defaultValue = "") String city,
//                                           @RequestParam(value = "country", defaultValue = "") String country,
//                                           @RequestParam(value = "zip", defaultValue = "") String zip
//    ) {
//        return guestUserHandler.confirmPurchase(sessionId, cardNumber, cardMonth, cardYear, cardHolder, cardCcv, cardId, buyerName, address, city, country, zip);
//    }


    @GetMapping("/getProductsByCategory")
    @ResponseBody
    public Response getProductsByCategory(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String categoryName) {
        return marketManager.getProductsByCategory(sessionId,categoryName);
    }

    @GetMapping("/getProductsByName")
    @ResponseBody
    public Response getProductsByName(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String Name) {
        return marketManager.getProductsByName(sessionId,Name);
    }

    @GetMapping("/getProductsBySubstring")
    @ResponseBody
    public Response getProductsBySubstring(
            @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
            @RequestParam(value = "categoryName", defaultValue = "") String subName) {
        return marketManager.getProductsBySubstring(sessionId,subName);
    }


    @GetMapping("/getShoppingCart")
    @ResponseBody
    public Response getShoppingCart(@RequestParam(value = "sessionId", defaultValue = "") String sessionId) {
        return marketManager.getShoppingCart(sessionId);
    }

//    @GetMapping("/viewBuyingPolicies")
//    @ResponseBody
//    public BuyingPolicyActionResultDTO viewBuyingPolicies(
//            @RequestParam(value = "sessionId", defaultValue = "") int sessionId,
//            @RequestParam(value = "storeId", defaultValue = "") int storeId
//    ) {
//        return marketManager.(sessionId, storeId);
//    }
}
