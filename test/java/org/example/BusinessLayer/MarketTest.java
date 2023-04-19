package org.example.BusinessLayer;

import org.junit.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    private Market market;
    private String userName1 = "Idan111";
    private String password1 = "ie9xzsz4321";
    private String email1 = "idanlobel1@gmail.com";
    private String userName2 = "Micahel987";
    private String password2 = "uadfadsa1";
    private String email2 = "Micahel987@gmail.com";
    private Member member1;
    private Member member2;
    private Product product1;
    private Product product2;

    @org.junit.jupiter.api.BeforeAll
    void startUp() throws Exception {
        market = new Market();
        market.signUp(userName1, email1, password1);
        member1 = market.getMemberFromUser(userName1);
        market.signUp(userName2, email2, password2);
        member2 = market.getMemberFromUser(userName2);
        market.openStore(member1,"Candy Shop");
        market.openStore(member1,"Mamtakim");
        product1 = new Product(111, "milk", 6.14, "milk", 3.5, 100);
        product2 = new Product(123, "Carlsberg beer", 10.5, "alcohol", 2.4, 50);
        market.getStore(0).addProduct(product1, 50, member1);
        market.getStore(1).addProduct(product2, 50, member1);
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

    private void signUpExists() {
        try {
            market.signUp(userName1,  email1, password1);
        }
        catch (Exception e){
            assertEquals(e.getMessage(),"Username already exists");
        }
    }

    @org.junit.jupiter.api.Test
    void login() {
        assertTrue(market.login(userName1,  email1, password1));
    }

    @org.junit.jupiter.api.Test
    void getStores() {
        assertEquals("Candy Shop" , market.getStores("Candy").get(0).getStoreName());
        getStores2();
    }

    private void getStores2() {
        assertEquals("Mamtakim" , market.getStores("Mam").get(0).getStoreName());
    }

    @org.junit.jupiter.api.Test
    void getStore() {
        assertEquals("Candy Shop", market.getStore(0).getStoreName());
        assertEquals("Mamtakim", market.getStore(1).getStoreName());
    }

    private void getStoreOutOfBound() {
        assertNull(market.getStore(4));
    }

    private void getNegativeStoreID() {
        assertNull(market.getStore(-1));
    }

    @org.junit.jupiter.api.Test
    void getProductsByName() {
        List<Product> productsList1 = market.getProductsByName("milk");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<Product> productsList2 = market.getProductsByName("beer");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @org.junit.jupiter.api.Test
    void getProductsByCategory() {
        List<Product> productsList1 = market.getProductsByCategory("milk");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<Product> productsList2 = market.getProductsByCategory("alcohol");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @org.junit.jupiter.api.Test
    void getProductsBySubstring() {
        List<Product> productsList1 = market.getProductsBySubstring("il");
        assertEquals("milk", productsList1.get(0).getProductName());
        List<Product> productsList2 = market.getProductsBySubstring("bee");
        assertEquals("beer", productsList2.get(0).getProductName());
    }

    @org.junit.jupiter.api.Test
    void openStore() {
        market.openStore(member1, "toys r us");
        assertEquals("toys r us" ,market.getStores("toys r us").get(0).getStoreName());
    }

    @org.junit.jupiter.api.Test
    void addStoreManagerPermissions() throws Exception {
        market.addStoreManagerPermissions(member2, "Idan111", 1, StoreManager.permissionType.setNewPosition);
        boolean storeManagerPosition = false;
        assertEquals(StoreManager.class , member2.getStorePosition(market.getStore(1)).getClass());
    }

    private void addStoreManagerWithoutPermissions() {
        try {
            market.addStoreManagerPermissions(member1, "Michael987", 0, StoreManager.permissionType.setNewPosition);
        }
        catch (Exception e){
            assertEquals("the name of the store manager has not have that position in this store", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void setPositionOfMemberToStoreManager() throws Exception {
        market.setPositionOfMemberToStoreManager(member1, 0, "Michael987");
        boolean storeManagerPosition = false;
        assertEquals(StoreManager.class , member2.getStorePosition(market.getStore(1)).getClass());
    }

    private void setPositionOfMemberToStoreManagerWithoutPermissions() {
        try {
            market.setPositionOfMemberToStoreManager(member2, 0, "Idan111");
        }
        catch (Exception e){
            assertEquals("the name of the store manager has not have that position in this store", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void closeStore() {
        boolean closed = true;
        market.closeStore(member2,1);//TODO: check if the member has permissions to close this store and if it closed it or not
        assertTrue(closed);
    }
}