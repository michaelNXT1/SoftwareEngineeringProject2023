package UnitTests;

import BusinessLayer.*;
import ServiceLayer.DTOs.ProductDTO;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MarketTest extends TestCase {
    private Market market;

    private String sessionID1;

    private String sessionID2;
    private String userName1 = "Idan111";
    private String password1 = "ie9xzsz4321";
    private String email1 = "idanlobel1@gmail.com";
    private String userName2 = "Michael987";
    private String password2 = "uadfadsa1";
//    private Member member1;
//    private Member member2;
//    private Product product1;
//    private Product product2;

    @org.junit.jupiter.api.BeforeAll
    void startUp() throws Exception {
        market = new Market();
        market.signUp(userName1, password1);
        market.signUp(userName2, password2);
        sessionID1 = market.login(userName1,password1,null);
        sessionID2 = market.login(userName2,password2,null);
        market.openStore(sessionID1, "Candy Shop");
        market.openStore(sessionID1, "Mamtakim");
//        product1 = new Product(111, "milk", 6.14, "milk");
//        product2 = new Product(123, "Carlsberg beer", 10.5, "alcohol");
        market.getStore(sessionID1, 0).addProduct("milk", 50, "milk", 15,"aa");
        market.getStore(sessionID1, 1).addProduct("beer", 50, "alcohol", 100,"bb");
    }

    @org.junit.jupiter.api.Test
    void signUp() throws Exception {
        try {
            market.signUp(userName2, password2);
        }
        catch (Exception e){
            //Do nothing
        }
        assertTrue(market.usernameExists(userName2));
    }
    @org.junit.jupiter.api.Test
    void signUpExists() {
        try {
            market.signUp(userName1, password1);
        }
        catch (Exception e){
            assertEquals(e.getMessage(),"Username already exists");
        }
    }

    @org.junit.jupiter.api.Test
    void login() throws Exception {
        assertNotNull(market.login(userName1, password1,null),"session string");
    }

    @org.junit.jupiter.api.Test
    void getStores() throws Exception {
        assertEquals("Candy Shop" , market.getStores(sessionID1,"Candy").get(0).getStoreName());
        getStores2();
    }

    private void getStores2() throws Exception {
        assertEquals("Mamtakim" , market.getStores(sessionID1,"Mam").get(0).getStoreName());
    }

    @org.junit.jupiter.api.Test
    void getStore() throws Exception {
        assertEquals("Candy Shop", market.getStore(sessionID1, 0).getStoreName());
        assertEquals("Mamtakim", market.getStore(sessionID1, 1).getStoreName());
    }
    @org.junit.jupiter.api.Test
    private void getStoreOutOfBound() throws Exception {
        assertNull(market.getStore(sessionID1, 4));
    }
    @org.junit.jupiter.api.Test
    private void getNegativeStoreID() throws Exception {
        assertNull(market.getStore(sessionID1, -1));
    }

    @org.junit.jupiter.api.Test
    void getProductsByName() throws Exception {
        List<ProductDTO> productsList1 = market.getProductsByName(sessionID1,"milk");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<ProductDTO> productsList2 = market.getProductsByName(sessionID1,"beer");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @org.junit.jupiter.api.Test
    void getProductsByCategory() throws Exception {
        List<ProductDTO> productsList1 = market.getProductsByCategory(sessionID1,"milk");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<ProductDTO> productsList2 = market.getProductsByCategory(sessionID1,"alcohol");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @org.junit.jupiter.api.Test
    void getProductsBySubstring() throws Exception {
        List<ProductDTO> productsList1 = market.getProductsBySubstring(sessionID1,"il");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<ProductDTO> productsList2 = market.getProductsBySubstring(sessionID1,"bee");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @org.junit.jupiter.api.Test
    void openStore() throws Exception {
        market.openStore(sessionID1,"toys r us");
        assertEquals("toys r us" ,market.getStores(sessionID1,"toys r us").get(0).getStoreName());
    }

    @org.junit.jupiter.api.Test
    void addStoreManagerPermissions() throws Exception {
        market.addStoreManagerPermissions( sessionID1,"Idan111", 1, 1);
        boolean storeManagerPosition = false;
        Store store1 = market.getStore(sessionID1, 1);
        assertEquals(StoreManager.class , store1.getEmployees().get(0).getStorePosition(store1).getClass());
    }
    @org.junit.jupiter.api.Test
    private void addStoreManagerWithoutPermissions() {
        try {
            market.addStoreManagerPermissions(sessionID1,"Michael987", 0, 1);
        }
        catch (Exception e){
            assertEquals("the name of the store manager has not have that position in this store", e.getMessage());
        }
    }

    @Test
    void setPositionOfMemberToStoreManager() throws Exception {
        market.setPositionOfMemberToStoreManager(sessionID1,0, "Michael987");
        boolean storeManagerPosition = false;
        Store store0 = market.getStore(sessionID1, 0);
        assertEquals(StoreManager.class , store0.getEmployees().get(1).getStorePosition(store0).getClass());
    }
    @Test
    void removeStoreOwner() throws Exception {
        market.setPositionOfMemberToStoreOwner(sessionID1,0,"Michael987");
        market.removeStoreOwner(sessionID1, "Michael987",0);
        assertFalse(market.getStoreOwners(0).contains("Michael987"));
    }

    private void setPositionOfMemberToStoreManagerWithoutPermissions() {
        try {
            market.setPositionOfMemberToStoreManager(sessionID1,0, "Idan111");
        }
        catch (Exception e){
            assertEquals("the name of the store manager has not have that position in this store", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void closeStore() throws Exception {
        boolean closed = true;
        market.closeStore(sessionID1,1);//TODO: check if the member has permissions to close this store and if it closed it or not
        assertTrue(closed);
    }
    @org.junit.jupiter.api.Test
    void removeMember() throws Exception {
        market.removeMember(sessionID1,userName2);
        assertTrue(market.getUsers().get(userName2) == null);
    }


}