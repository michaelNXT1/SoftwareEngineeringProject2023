package UnitTests;

import BusinessLayer.Market;
import BusinessLayer.Member;
import BusinessLayer.Product;
import BusinessLayer.Store;


import ServiceLayer.DTOs.ProductDTO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class storeTests {
    Market market = new Market(null,true);
    private Store store;
    private Member employee;
    private Product product;


    @BeforeEach
    void setUp() throws Exception {
        employee = new Member("shanihd99",  "123456");
        store = new Store(1, "my_store", employee);
        product = store.addProduct("barbi", 50, "toys", 5,"aa", ProductDTO.PurchaseType.BUY_IT_NOW);
    }

    @AfterEach
    public void tearDown() {
        market.clearAllData();
    }

    @Test
    void testGetStoreName() {
        assertEquals("my_store", store.getStoreName());
    }

    @Test
    void testGetProducts() {
        assertEquals(5, store.getProducts().getAllProducts().get(0).getQuantity());
    }

    @Test
    void testUpdateProductQuantity() {
//        assertFalse(store.addToProductQuantity(product, -11));
//        assertTrue(store.addToProductQuantity(product, -9));
        assertEquals(5, store.getProducts().getAllProducts().get(0).getQuantity());
    }

    @org.junit.jupiter.api.Test
    void testAddPurchase() {
   //     Purchase purchase = new Purchase();
      //  store.addPurchase(purchase);
        //assertEquals(1, store.getPurchaseList().size());
    }

    @Test
    void testAddProduct() throws Exception {
        assertThrows(Exception.class, () -> store.addProduct("bratz", 60.9, "toy", 7,"vv", ProductDTO.PurchaseType.BUY_IT_NOW));
        assertEquals("barbi", store.getProducts().getAllProducts().stream().filter(p -> p.getProductId() == product.getProductId()).findFirst().orElse(null).getProductName());
    }

    @Test
    void testRemoveProduct() throws Exception {
        int productId = product.getProductId();
        store.removeProduct(productId);
        assertNull(store.getProducts().getAllProducts().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null));
    }

    @Test
    void testIsOpen() {
        assertFalse(store.isOpen());
        store.setOpen(true);
        assertTrue(store.isOpen());
    }

    @Test
    void testGetStoreId() {
        assertEquals(1, store.getStoreId());
    }

    @Test
    void testGetProduct() throws Exception {
        assertEquals(product.getProductId(), store.getProduct(product.getProductId()).getProductId());
        assertThrows(Exception.class, () -> store.getProduct(0));
    }

    @Test
    void testEditProductName() throws Exception {
        int productId = product.getProductId();
        store.editProductName(productId, "New Name");
        assertEquals("New Name", store.getProducts().getAllProducts().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null).getProductName());
        assertThrows(Exception.class, () -> store.editProductName(0, "Invalid Product"));
        //assertThrows(Exception.class, () -> store.editProductName(productId, "barbi"));
    }


    @Test
    void testEditProductPrice() throws Exception {
        int productId = product.getProductId();
        store.editProductPrice(productId, 20);
        assertEquals(20.0, store.getProducts().getAllProducts().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null).getPrice());
        assertThrows(Exception.class, () -> store.editProductPrice(0, 20));

    }

    @Test
    public void testEditProductCategory() throws Exception {
        store.editProductCategory(product.getProductId(), "New Category");
        assertEquals("New Category", store.getProduct(product.getProductId()).getCategory());
    }




}

