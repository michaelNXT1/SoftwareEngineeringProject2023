
package UnitTests;

import BusinessLayer.*;
import ServiceLayer.DTOs.ProductDTO;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarketTest extends TestCase {
    private Market market;

    private String sessionID1;
    private String sessionID2;
    private String sessionID3;
    private String sessionID4;
    private String userName1 = "Idan111";
    private String password1 = "ie9xzsz4321";
    private String userName2 = "Michael987";
    private String password2 = "uadfadsa1";
    private String userName3 = "Alon555";
    private String password3 = "sssdddaaa";
    private String userName4 = "Shoham735412";
    private String password4 = "qwerty";
    @BeforeEach
    void startUp() throws Exception {
        market = new Market("testFile.txt",true);
        market.signUp(userName1, password1);
        market.signUp(userName2, password2);
        market.signUpSystemManager(userName3, password3);
        market.signUp(userName4, password4);
        sessionID1 = market.login(userName1,password1,null);
        sessionID2 = market.login(userName2,password2,null);
        sessionID3 = market.loginSystemManager(userName3,password3,null);
        sessionID4 = market.login(userName4,password4,null);
        market.openStore(sessionID1, "Candy Shop");
        market.openStore(sessionID1, "Mamtakim");
        market.getStore(sessionID1, 0).addProduct("milk", 50, "milk", 15,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
        market.getStore(sessionID1, 1).addProduct("beer", 50, "alcohol", 100,"bb", ProductDTO.PurchaseType.BUY_IT_NOW);
    }

    @Test
    void signUp() throws Exception {
        try {
            market.signUp(userName2, password2);
        }
        catch (Exception e){
            //Do nothing
        }
        assertTrue(market.usernameExists(userName2));
    }
    @Test
    void signUpExists() {
        try {
            market.signUp(userName1, password1);
        }
        catch (Exception e){
            assertEquals(e.getMessage(),"Username already exists");
        }
    }

    @Test
    void login() throws Exception {
        market.logout(sessionID1);
        assertNotNull(market.login(userName1, password1,null),"session string");
    }

    @Test
    void getStores() throws Exception {
        assertEquals("Candy Shop" , market.getStores(sessionID1,"Candy").get(0).getStoreName());
        getStores2();
    }
    @Test
    private void getStores2() throws Exception {
        assertEquals("Mamtakim" , market.getStores(sessionID1,"Mam").get(0).getStoreName());
    }

    @Test
    void getStore() throws Exception {
        assertEquals("Candy Shop", market.getStore(sessionID1, 0).getStoreName());
        assertEquals("Mamtakim", market.getStore(sessionID1, 1).getStoreName());
    }
    @Test
    private void getStoreOutOfBound() throws Exception {
        assertNull(market.getStore(sessionID1, 4));
    }
    @Test
    private void getNegativeStoreID() throws Exception {
        assertNull(market.getStore(sessionID1, -1));
    }

    @Test
    void getProductsByName() throws Exception {
        List<ProductDTO> productsList1 = market.getProductsByName(sessionID1,"milk");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<ProductDTO> productsList2 = market.getProductsByName(sessionID1,"beer");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @Test
    void getProductsByCategory() throws Exception {
        List<ProductDTO> productsList1 = market.getProductsByCategory(sessionID1,"milk");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<ProductDTO> productsList2 = market.getProductsByCategory(sessionID1,"alcohol");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @Test
    void getProductsBySubstring() throws Exception {
        List<ProductDTO> productsList1 = market.getProductsBySubstring(sessionID1,"il");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<ProductDTO> productsList2 = market.getProductsBySubstring(sessionID1,"bee");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @Test
    void openStore() throws Exception {
        market.openStore(sessionID1,"toys r us");
        assertEquals("toys r us" ,market.getStores(sessionID1,"toys r us").get(0).getStoreName());
    }

    @Test
    void addStoreManagerPermissions() throws Exception {
        market.addStoreManagerPermissions( sessionID1,"Idan111", 1, 1);
        boolean storeManagerPosition = false;
        Store store1 = market.getStore(sessionID1, 1);
        assertEquals(StoreFounder.class , store1.getEmployees().get(0).getStorePosition(store1).getClass());
    }
    @Test
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
        assertEquals(StoreFounder.class , store0.getEmployees().get(1).getStorePosition(store0).getClass());
    }
    @Test
    void removeStoreOwner() throws Exception {
        market.setPositionOfMemberToStoreOwner(sessionID1,0,"Michael987");
        market.removeStoreOwner(sessionID1, "Michael987",0);
        assertFalse(market.getStoreOwners(0).contains("Michael987"));
    }

    @Test
    private void setPositionOfMemberToStoreManagerWithoutPermissions() {
        try {
            market.setPositionOfMemberToStoreManager(sessionID1,0, "Idan111");
        }
        catch (Exception e){
            assertEquals("the name of the store manager has not have that position in this store", e.getMessage());
        }
    }

    @Test
    void closeStore() throws Exception {
        boolean closed = true;
        market.closeStore(sessionID1,1);//TODO: check if the member has permissions to close this store and if it closed it or not
        assertTrue(closed);
    }
    @Test
    void removeMemberWithPositions() throws Exception {
        Throwable exception = assertThrows(Exception.class, () -> market.removeMember(sessionID3,userName1));
        assertEquals("cannot remove member with positions in the market", exception.getMessage());
    }

    @Test
    void removeMemberWithoutPositions() throws Exception {
        market.removeMember(sessionID3,userName4);
        assertTrue(!market.getUsers().containsKey(userName4));
    }


}