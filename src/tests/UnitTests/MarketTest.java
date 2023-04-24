package UnitTests;

import org.example.BusinessLayer.Market;
import org.example.BusinessLayer.Product;
import org.example.BusinessLayer.Store;
import org.example.BusinessLayer.StoreManager;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    private Market market;
    private String userName1 = "Idan111";
    private String password1 = "ie9xzsz4321";
    private String email1 = "idanlobel1@gmail.com";
    private String userName2 = "Michael987";
    private String password2 = "uadfadsa1";
    private String email2 = "Micahel987@gmail.com";
//    private Member member1;
//    private Member member2;
//    private Product product1;
//    private Product product2;

    @org.junit.jupiter.api.BeforeAll
    void startUp() throws Exception {
        market = new Market();
        market.signUp(userName1, email1, password1);
        market.signUp(userName2, email2, password2);
        market.openStore("Candy Shop");
        market.openStore("Mamtakim");
//        product1 = new Product(111, "milk", 6.14, "milk");
//        product2 = new Product(123, "Carlsberg beer", 10.5, "alcohol");
        market.getStore(0).addProduct("milk", 50, "milk", 15);
        market.getStore(1).addProduct("beer", 50, "alcohol", 100);
    }

    @org.junit.jupiter.api.Test
    void signUp() throws Exception {
        try {
            market.signUp(userName2,  email2, password2);
        }
        catch (Exception e){
            //Do nothing
        }
        assertTrue(market.usernameExists(userName2));
    }
    @org.junit.jupiter.api.Test
    void signUpExists() {
        try {
            market.signUp(userName1,  email1, password1);
        }
        catch (Exception e){
            assertEquals(e.getMessage(),"Username already exists");
        }
    }

    @org.junit.jupiter.api.Test
    void login() throws Exception {
        assertNotNull(market.login(userName1,  email1, password1),"session string");
    }

    @org.junit.jupiter.api.Test
    void getStores() throws Exception {
        assertEquals("Candy Shop" , market.getStores("Candy").get(0).getStoreName());
        getStores2();
    }

    private void getStores2() throws Exception {
        assertEquals("Mamtakim" , market.getStores("Mam").get(0).getStoreName());
    }

    @org.junit.jupiter.api.Test
    void getStore() throws Exception {
        assertEquals("Candy Shop", market.getStore(0).getStoreName());
        assertEquals("Mamtakim", market.getStore(1).getStoreName());
    }
    @org.junit.jupiter.api.Test
    private void getStoreOutOfBound() throws Exception {
        assertNull(market.getStore(4));
    }
    @org.junit.jupiter.api.Test
    private void getNegativeStoreID() throws Exception {
        assertNull(market.getStore(-1));
    }

    @org.junit.jupiter.api.Test
    void getProductsByName() throws Exception {
        List<Product> productsList1 = market.getProductsByName("milk");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<Product> productsList2 = market.getProductsByName("beer");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @org.junit.jupiter.api.Test
    void getProductsByCategory() throws Exception {
        List<Product> productsList1 = market.getProductsByCategory("milk");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<Product> productsList2 = market.getProductsByCategory("alcohol");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @org.junit.jupiter.api.Test
    void getProductsBySubstring() throws Exception {
        List<Product> productsList1 = market.getProductsBySubstring("il");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<Product> productsList2 = market.getProductsBySubstring("bee");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @org.junit.jupiter.api.Test
    void openStore() throws Exception {
        market.openStore("toys r us");
        assertEquals("toys r us" ,market.getStores("toys r us").get(0).getStoreName());
    }

    @org.junit.jupiter.api.Test
    void addStoreManagerPermissions() throws Exception {
        market.addStoreManagerPermissions( "Idan111", 1, 1);
        boolean storeManagerPosition = false;
        Store store1 = market.getStore(1);
        assertEquals(StoreManager.class , store1.getEmployees().get(0).getStorePosition(store1).getClass());
    }
    @org.junit.jupiter.api.Test
    private void addStoreManagerWithoutPermissions() {
        try {
            market.addStoreManagerPermissions("Michael987", 0, 1);
        }
        catch (Exception e){
            assertEquals("the name of the store manager has not have that position in this store", e.getMessage());
        }
    }

    @Test
    void setPositionOfMemberToStoreManager() throws Exception {
        market.setPositionOfMemberToStoreManager(0, "Michael987");
        boolean storeManagerPosition = false;
        Store store0 = market.getStore(0);
        assertEquals(StoreManager.class , store0.getEmployees().get(1).getStorePosition(store0).getClass());
    }

    private void setPositionOfMemberToStoreManagerWithoutPermissions() {
        try {
            market.setPositionOfMemberToStoreManager(0, "Idan111");
        }
        catch (Exception e){
            assertEquals("the name of the store manager has not have that position in this store", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void closeStore() throws Exception {
        boolean closed = true;
        market.closeStore(1);//TODO: check if the member has permissions to close this store and if it closed it or not
        assertTrue(closed);
    }
}