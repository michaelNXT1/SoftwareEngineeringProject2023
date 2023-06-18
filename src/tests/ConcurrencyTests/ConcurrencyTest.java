package ConcurrencyTests;


import BusinessLayer.Market;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyTest extends TestCase {
    Market market = new Market(null,true);
    private final static int THREADS=10;

    public ConcurrencyTest() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        market.signUpSystemManager("alon","aa");

    }

    @AfterEach
    public void tearDown() {
        market.clearAllData();
    }

    @Test
    public void testBuyProductsMultipleBuysSucess() throws Exception {
        market.signUp("master","1234");
        String id = market.login("master","1234",null);
        market.addPaymentMethod(id,"123","06","2026","540", "Micheal", "260589064");
        int storeid = market.openStore(id,"newStore");
        int productID =setUpStoreWithAmount(id,storeid,100);
        buyFromStoreAmount(storeid,productID,5);
        try {
            assertEquals(100-(THREADS*5),market.getStore(id, storeid).getProduct(productID).getQuantity());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testBuyProductsMultipleBuysSucessMoreThenInventory() throws Exception {
        market.signUp("master","1234");
        String id = market.login("master","1234",null);
        market.addPaymentMethod(id,"123","06","2026","540", "Micheal", "260589064");
        int storeId = market.openStore(id,"newStore");
        int productId = setUpStoreWithAmount(id, storeId,10);
        buyFromStoreAmount(storeId,productId,1);
        try {
            assertEquals(1,market.getStore(id, storeId).getProduct(productId).getProductId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testBuyProductsMultipleBuysFailMoreThenInventoryHistory() throws Exception {
        market.signUp("master","1234");
        String id = market.login("master","1234",null);
        market.addPaymentMethod(id,"123","06","2026","540", "Micheal", "260589064");
        int storeId = market.openStore(id,"newStore");
        int productID = setUpStoreWithAmount(id,storeId,7);
        buyFromStoreAmount(storeId,productID,10);
        try {
            assertEquals(0,market.getStore(id, storeId).getPurchaseList().size());
        } catch (Exception e) {
            fail();
        }
    }

    private int setUpStoreWithAmount(String sessioID, int StoreID,int amount) throws Exception {
        return market.addProduct(sessioID,StoreID,"bamba",5,"food",amount,"yammmmm").getProductId();
    }

    /*
    Sets THREADS to buy from storeId with the amount on the parameter
     */
    private void buyFromStoreAmount(int storeId,int productId,int amount) {
        List<Thread> threadList = new ArrayList();
        AtomicInteger nameId = new AtomicInteger(0);
        for(int i = 0;i<THREADS;i++) {
            Thread t1 = new Thread(() -> {
                String newId;
                try {
                    String name = String.format("bob%d", nameId.getAndIncrement());
                    market.signUp(name, "123");
                    newId = market.login(name, "123",null);
                    market.addPaymentMethod(newId, "123", "13", "12", "232", "Micheal", "260589064");
                    market.addProductToCart(newId, storeId, productId, amount);
                    confirmPurchase(newId);
                } catch (Exception e) {
                }
            });
            threadList.add(t1);
        }
        for(Thread t : threadList){
            t.start();
        }
        for(Thread t : threadList){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void confirmPurchase(String sessionId) throws Exception {
        market.purchaseShoppingCart(sessionId);
    }



    /// PASSED TESTS////

    @Test
    public void testRegistermultipleTimes() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        for(int i = 0;i<THREADS;i++){
            Thread t = new Thread(()->{
                try {
                    market.signUp("bob","1234");
                } catch (Exception e) {
                }
                count.incrementAndGet();
            });
            threads.add(t);
        }
        for(Thread t : threads){
            t.start();
        }
        while(count.get()<THREADS){}

        assertEquals(1,market.getUsers().size());


    }

    @Test
    public void testRegistermultipleUsers(){
        List<Thread> threads = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger nameId = new AtomicInteger(0);
        Map<String,String> idsCount = new ConcurrentHashMap<>();
        for(int i = 0;i<THREADS;i++){
            Thread t = new Thread(()->{
                try {
                    String name = String.format("bob%d",nameId.getAndIncrement());
                    market.signUp(name,"123");
                   idsCount.put(name,"123");
                } catch (Exception e) {
                }
                count.incrementAndGet();
            });
            threads.add(t);
        }
        for(Thread t : threads){
            t.start();
        }
        while(count.get()<THREADS){}

        for(String val : idsCount.values()) {
            assertEquals("123", val);
        }
        assertEquals(idsCount.size(),count.get());


    }

    @Test
    public void testStartMultipleSessions() throws Exception {
        List<Thread> threads = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        Map<String,String> idsCount = new ConcurrentHashMap<>();
        AtomicInteger nameId = new AtomicInteger(0);
        for(int i = 0;i < THREADS;i++){
            Thread t = new Thread(()->{
                try {
                    String name = String.format("bob%d",nameId.getAndIncrement());
                    market.signUp(name,"123");
                    market.login(name,"123",null);
                    idsCount.put(name,"123");
                } catch (Exception e) {
                }
                count.incrementAndGet();
            });
            threads.add(t);
        }
        for(Thread t : threads){
            t.start();
        }
        while(count.get()<THREADS){}
        for(String name : idsCount.keySet()) {
            assertNotNull(market.getSessionID(name));
        }
    }

    @Test
    public void testOpenMultipleStores(){
        List<Thread> threads = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger nameId = new AtomicInteger(1);
        Map<Integer,Integer> idsCount = new ConcurrentHashMap<>();
        for(int i = 0;i<THREADS;i++){
            Thread t = new Thread(()->{
                String name = String.valueOf(nameId.getAndIncrement());
                String id = "";
                try {
                    market.signUp(name,"123");
                    id = market.login(name,"123",null);
                } catch (Exception e) {
                }
                int storeId = 0;
                try {
                    storeId = market.openStore(id,"newStore");
                    idsCount.put(storeId,1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                count.incrementAndGet();
            });
            threads.add(t);
        }
        for(Thread t : threads){
            t.start();
        }
        for(Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

       assertEquals(idsCount.size(),market.getStores().size());

    }

    @Test
    public void testMultipleManagerAppoint() throws Exception {
        market.signUp("BigBoss","123");
        String sessionID = market.login("BigBoss","123",null);
        int storeId = market.openStore(sessionID,"newStore");

        AtomicInteger nameId = new AtomicInteger(1);
        List<Thread> threads = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);


        for(int i = 0;i<THREADS;i++){

            Thread t = new Thread(()->{
                String name = String.valueOf(nameId.getAndIncrement());
                try {
                    market.signUp(name,"123");
                    market.setPositionOfMemberToStoreManager(sessionID,storeId, name);
                } catch (Exception e) {
                }
                count.incrementAndGet();
            });
            threads.add(t);
        }
        for(Thread t : threads){
            t.start();
        }
        while(count.get()<THREADS){}

        assertEquals(THREADS,market.getStore(sessionID, storeId).getManagers().size());
    }
}
