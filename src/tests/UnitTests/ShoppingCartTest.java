package UnitTests;

import BusinessLayer.*;
import BusinessLayer.ExternalSystems.IPaymentSystem;
import BusinessLayer.ExternalSystems.ISupplySystem;
import BusinessLayer.ExternalSystems.PaymentSystem;
import BusinessLayer.ExternalSystems.SupplySystem;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.PurchaseProductDTO;
import ServiceLayer.DTOs.ShoppingBagDTO;
import ServiceLayer.DTOs.StoreDTO;
import Utils.Pair;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ShoppingCartTest extends TestCase {


    private ShoppingCart shoppingCart;
    private Market market;
    private Product product1;
    private Product product2;
    private Product product3;
    int storeId1;
    String sessionId1;
    String sessionId2;
    private Member member;
    private final String userName1 = "Idan111";
    private final String password1 = "ie9xzsz4321";
    private String userName2 = "Michael987";
    private String password2 = "uadfadsa1";
    private String userName3 = "Alon555";
    private String password3 = "sssdddaaa";
    private String userName4 = "Shoham735412";
    private String password4 = "qwerty";
    private ISupplySystem supplySystem;
    private IPaymentSystem paymentSystem;

    @BeforeEach
    void beforeEach() throws Exception {
        market = new Market("testFile.txt",true);
        market.signUp(userName1, password1);
        market.signUpSystemManager(userName2, password2);
        market.signUp(userName3,  password3);
        market.signUp(userName4,  password4);
        sessionId1 = market.login(userName1,  password1,null);
        sessionId2 = market.loginSystemManager(userName2, password2,null);
        storeId1 = market.openStore(sessionId1, "storeName");
        shoppingCart = new ShoppingCart(userName1);
        //product1 = new Product(storeId1,1,"wine", 3, "pleasures", 20, "alcohol", ProductDTO.PurchaseType.BUY_IT_NOW);
        //product2 = new Product(storeId1,2, "cheese", 9.5, "milk", 15, "fads", ProductDTO.PurchaseType.BUY_IT_NOW);
        //product3 = new Product(storeId1,3, "steak", 120.0, "meat",5,"asd", ProductDTO.PurchaseType.BUY_IT_NOW);
        supplySystem = new SupplySystem();
        paymentSystem = new PaymentSystem();
        market.openStore(sessionId1, "Candy Shop");
        Store store1 = market.getStore(sessionId1, storeId1);
        product1 = store1.addProduct("wine", 3, "pleasures", 20, "alcohol", ProductDTO.PurchaseType.BUY_IT_NOW);
        //product2 = store1.addProduct("cheese", 9.5, "milk", 15, "fads", ProductDTO.PurchaseType.BUY_IT_NOW);
        //product3 = store1.addProduct("steak", 120.0, "meat", 5, "asd", ProductDTO.PurchaseType.BUY_IT_NOW);
    }

    @Test
    void purchaseShoppingCartWithMockPaymentSystem() throws Exception {
        SupplySystemProxy supplySystemProxy = mock(SupplySystemProxy.class);
        PaymentSystemProxy ps = new PaymentSystemProxy();
        market.setPaymentSystem(ps);
        market.setSupplySystem(supplySystemProxy);
        market.addSupplyDetails(sessionId1, "shoham", "alexnder", "beer sheva", "Israel","806459");
        market.addPaymentMethod(sessionId1, "123456789", "April","2023","489","shoham","508479063");
        Exception exception = assertThrows(Exception.class, () -> market.purchaseShoppingCart(sessionId1));
        assertEquals("Purchase failed, supply system hasn't managed to charge", exception.getMessage());
    }

    @Test
    void purchaseShoppingCartWithMockSupplySystem() throws Exception {
        PaymentSystemProxy paymentSystemProxy = mock(PaymentSystemProxy.class);
        SupplySystemProxy ss = new SupplySystemProxy();
        market.setSupplySystem(ss);
        market.setPaymentSystem(paymentSystemProxy);
        market.addSupplyDetails(sessionId1, "shoham", "alexnder", "beer sheva", "Israel","806459");
        market.addPaymentMethod(sessionId1, "123456789", "April","2023","489","shoham","508479063");
        Exception exception = assertThrows(Exception.class, () -> market.purchaseShoppingCart(sessionId1));
        assertEquals("Purchase failed, supply system hasn't managed to charge", exception.getMessage());
    }
    @Test
    void changeProductQuantity() throws Exception {
        market.changeProductQuantity(sessionId1, storeId1, product1.getProductId(),12);
        //int actualQuantity = market.getShoppingCart(sessionId1).shoppingBags.get(0).getProductList().get(new ProductDTO(product2)); //can't access to shopping bags
        assertEquals(product1.getQuantity()-8,12);
    }

    @Test
    void removeProduct() throws Exception {
        market.addProductToCart(sessionId1, storeId1, product1.getProductId(), 2);
        market.removeProductFromCart(sessionId1, storeId1, product1.getProductId());
        assertTrue(market.getShoppingCart(sessionId1).shoppingBags.get(0).getProductList().isEmpty());
    }

    @Test
    void purchaseShoppingCart() throws Exception {
        Purchase purchase = new Purchase();
        ShoppingBag shoppingBag1 = new ShoppingBag();
        //Pair<PurchaseProduct, Boolean> pair1 = shoppingBag1.purchaseProduct(product1.getProductId(), purchase);
        //Pair<PurchaseProduct, Boolean> pair2 = shoppingBag1.purchaseProduct(product2.getProductId(), purchase);
        //Pair<PurchaseProduct, Boolean> pair3 = shoppingBag1.purchaseProduct(product3.getProductId(), purchase);
        //PurchaseProduct purchaseProduct1 = new PurchaseProduct(product1, 5, storeId1, 1);
        //PurchaseProduct purchaseProduct2 = new PurchaseProduct(product2, 10, storeId1, 1);
        //purchase.addProduct(purchaseProduct1);
        //purchase.addProduct(purchaseProduct2);
        SupplySystemProxy supplySystemProxy = new SupplySystemProxy();
        PaymentSystemProxy paymentSystemProxy = new PaymentSystemProxy();
        // market.setSupplySystem(supplySystemProxy);
        market.addSupplyDetails(sessionId1, "shoham", "alexnder", "beer sheva", "Israel","806459");
        //market.setPaymentSystem(paymentSystemProxy);
        market.addPaymentMethod(sessionId1, "123456789", "April","2023","450","shoham","508479063");
        SupplySystemProxy.succeedSupply = true;
        product2 = market.getStore(sessionId1, storeId1).addProduct("cheese", 9.5, "milk", 15, "fads", ProductDTO.PurchaseType.BUY_IT_NOW);
        market.addProductToCart(sessionId1, storeId1, product2.getProductId(), 1);
        //market.addProductToCart(sessionId1, storeId1, product2.getProductId(), 3);
//        List<ProductDTO> productDTOS = new LinkedList<>();
//        List<ShoppingBagDTO> shoppingBags = market.getShoppingCart(sessionId1).shoppingBags;
//        productDTOS.addAll(shoppingBags.stream().flatMap(shoppingBagsDTOs->shoppingBagsDTOs.getProductList().keySet().stream()).collect(Collectors.toList()));
//        List<PurchaseProductDTO> purchaseProductDTOList = market.purchaseShoppingCart(sessionId1).getProductDTOList();
//        List<ProductDTO> purchasesProductList = new LinkedList<>();
//        for(PurchaseProductDTO purchaseProductDTO: purchaseProductDTOList){
//            purchaseProductDTO.
//            Product product = market.getProduct(sessionId1, storeId1, product1)
//        }
        Store store1 = market.getStore(sessionId1, storeId1);
        StoreDTO storeDTO1 = new StoreDTO(store1);
        //Assertions.assertIterableEquals(market.getStoresPurchases(sessionId2).entrySet().stream().findFirst().get().getValue().get(0).getProductDTOList(), market.purchaseShoppingCart(sessionId1).getProductDTOList());
        //List<PurchaseProductDTO> expectedList = new LinkedList<>();
        assertEquals(9.5, market.purchaseShoppingCart(sessionId1).getTotalPrice());
    }
    @Test
    void purchaseShoppingCartWhenSupplySystemFailed() throws Exception {
        SupplySystemProxy ss = new SupplySystemProxy();
        ss.succeedSupply = false;
        market.setSupplySystem(ss);
        market.addSupplyDetails(sessionId1, "shoham", "alexnder", "beer sheva", "Israel","806459");
        market.addPaymentMethod(sessionId1, "123456789", "April","2023","489","shoham","508479063");

        Exception exception = assertThrows(Exception.class, () -> market.purchaseShoppingCart(sessionId1));
        assertEquals("Purchase failed, supply system hasn't managed to charge", exception.getMessage());
    }
    @Test
    void purchaseShoppingCartWhenPaymentSystemFailed() throws Exception {
        PaymentSystemProxy ps = new PaymentSystemProxy();
        ps.succeedPayment = false;
        market.setPaymentSystem(ps);
        market.addSupplyDetails(sessionId1, "shoham", "alexnder", "beer sheva", "Israel","806459");
        market.addPaymentMethod(sessionId1, "123456789", "April","2023","489","shoham","508479063");

        Exception exception = assertThrows(Exception.class, () -> market.purchaseShoppingCart(sessionId1));
        assertEquals("Purchase failed, payment system hasn't managed to charge", exception.getMessage());
    }

    //טסט שאין מספיק מוצרים במלאי
    //מערכת כספים לא עובד והמלאי לא משתנה ואין תשלום
    //שימוש בmocks
    //
}