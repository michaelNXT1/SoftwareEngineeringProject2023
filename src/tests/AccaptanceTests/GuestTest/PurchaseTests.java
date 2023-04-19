//package AccaptanceTests.bridge;
//
//import AccaptanceTests.ServiceTests;
//import org.example.BusinessLayer.Member;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class PurchaseTests extends ServiceTests {
//    /*
//     * USE CASES 2.8.1-2.8.4
//     *
//     * */
//    @Before
//    public void setUp(){
//        super.setUp();
//        int sessionId = 1;
//        int storeID = 1;
//
//
//        login("hanamaru", "abc@gmail.com","12345");
//        openStore(new Member("hanamaru", "abc@gmail.com","12345"), "newStore");
//        addProductToStore(sessionId, 1, storeID, 5);
//        addProductToStore(sessionId, 2, storeID, 5);
//        logout(sessionId);
//
//        login("jjjj", "abcג@gmail.com","12345");
//        openStore(new Member("hanamaru", "abc@gmail.com","12345"), "jjj'sStore");
//        addProductToStore(sessionId, 1, storeID, 10);
//        logout(sessionId);
//    }
//
//    @After
//    public void tearDown(){
//        clearDB();
//
////        Database.userToId.clear();
////        Database.userToStore.clear();
//    }
//
//
//
//    @Test
//    public void testPurchaseSuccessful(){
//
//
//        login("hanamaru", "abc@gmail.com","12345");
//
//
//        logout(Database.sessionId);
//
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),1, 5);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),2, 5);
//        assertTrue(buyCart(Database.sessionId, "12345678", "04", "2021", "me", "777",
//                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
//    }
//
//    @Test
//    public void testPurchaseFailureBadPolicy(){
//
//        setupSystem("Mock Config", "Mock Config","");
//        Database.sessionId = startSession();
//
//        login(Database.sessionId, "chika", "12345");
//        changeBuyingPolicy(Database.sessionId, true, Database.userToStore.get("chika"),"No one is allowed");
//
//        logout(Database.sessionId);
//
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),1, 5);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),2, 5);
//        assertFalse(buyCart(Database.sessionId, "12345678", "04", "2021", "me", "777",
//                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
//
//    }
//
//    @Test
//    public void testPurchaseFailureNotEnoughItemsInStore(){
//
//        setupSystem("Mock Config", "Mock Config","");
//        Database.sessionId = startSession();
//
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),1, 500);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),2, 500);
//        assertFalse(buyCart(Database.sessionId, "12345678", "04", "2021", "me", "777",
//                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
//
//    }
//
//    @Test
//    public void testPurchaseFailedSupplySystem(){
//
//        setupSystem("No supplies", "Mock Config","");
//        Database.sessionId = startSession();
//
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),1, 5);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),2, 5);
//        assertFalse(buyCart(Database.sessionId, "12345678", "04", "2021", "me", "777",
//                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
//    }
//
//    @Test
//    public void testPurchaseFailedPaymentSystem(){
//
//        setupSystem("Mock Config", "No payments","");
//        Database.sessionId = startSession();
//
//        PaymentSystemProxy.testing = true;
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),1, 5);
//        addToCart(Database.sessionId, Database.userToStore.get("chika"),2, 5);
//        assertFalse(buyCart(Database.sessionId, "12345678", "04", "2021", "me", "777",
//                "12123123", "me", "1428 Elm Street", "Springwood", "Ohio, United States", "12345"));
//    }
//
//
//}