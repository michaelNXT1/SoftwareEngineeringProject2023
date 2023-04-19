package Tests;

import org.junit.Test;

import org.example.BusinessLayer.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StoreTests {
    private static final int STORE_ID = 1;
    private static final String STORE_NAME = "Test Store";
    private static final String EMPLOYEE_NAME = "Test Employee";
    private static final String PRODUCT_NAME = "Test Product";
    private static final double PRODUCT_PRICE = 9.99;
    private static final String PRODUCT_CATEGORY = "Test Category";
    private static final int PRODUCT_QUANTITY = 10;

    private Store store;
    private Member employee;
    private Product product;

    @BeforeEach
    void setUp() throws Exception {
        employee = new Member("shanihd99","shanihd99@gmail.com","123456");
        store = new Store(STORE_ID, STORE_NAME, employee);
        product = store.addProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_CATEGORY, PRODUCT_QUANTITY);
    }

    @Test
    void testGetStoreName() {
        assertEquals(STORE_NAME, store.getStoreName());
    }

    @Test
    void testGetProducts() {
        assertEquals(PRODUCT_QUANTITY, store.getProducts().get(product));
    }

    @Test
    void testUpdateProductQuantity() {
        assertFalse(store.updateProductQuantity(product, -11));
        assertTrue(store.updateProductQuantity(product, -9));
        assertEquals(PRODUCT_QUANTITY-9, store.getProducts().get(product));
    }

    @Test
    void testAddPurchase() {
        Purchase purchase = new Purchase();
        store.addPurchase(purchase);
        assertEquals(1, store.getPurchaseList().size());
    }

    @Test
    void testAddProduct() throws Exception {
        assertThrows(Exception.class, () -> store.addProduct(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_CATEGORY, PRODUCT_QUANTITY));
        assertEquals(PRODUCT_NAME, store.getProducts().keySet().stream().filter(p -> p.getProductId() == product.getProductId()).findFirst().orElse(null).getProductName());
    }

    @Test
    void testRemoveProduct() throws Exception {
        int productId = product.getProductId();
        store.removeProduct(productId);
        assertNull(store.getProducts().keySet().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null));
    }

    @Test
    void testIsOpen() {
        assertFalse(store.isOpen());
        store.setOpen(true);
        assertTrue(store.isOpen());
    }

    @Test
    void testGetStoreId() {
        assertEquals(STORE_ID, store.getStoreId());
    }

    @Test
    void testGetProduct() throws Exception {
        assertEquals(product, store.getProduct(product.getProductId()));
        assertThrows(Exception.class, () -> store.getProduct(0));
    }

    @Test
    void testEditProductName() throws Exception {
        int productId = product.getProductId();
        store.editProductName(productId, "New Name");
        assertEquals("New Name", store.getProducts().keySet().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null).getProductName());
        assertThrows(Exception.class, () -> store.editProductName(0, "Invalid Product"));
        assertThrows(Exception.class, () -> store.editProductName(productId, PRODUCT_NAME));
    }

    @Test
    void testEditProductPrice() throws Exception {
        int productId = product.getProductId();
        store.editProductPrice(productId, 19.99);
        assertEquals(19.99, store.getProducts().keySet().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null).getPrice());
        assertThrows(Exception.class, () -> store.editProductPrice(0, 19.99));
    }

    @Test


