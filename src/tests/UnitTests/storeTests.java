package UnitTests;

import BusinessLayer.Member;
import BusinessLayer.Product;
import BusinessLayer.Store;


import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class storeTests {
    private Store store;
    private Member employee;
    private Product product;


    @BeforeEach
    void setUp() throws Exception {
        employee = new Member("shanihd99",  "123456");
        store = new Store(1, "my_store", employee);
        product = store.addProduct("barbi", 50, "toys", 5,"aa");
    }

    @org.junit.jupiter.api.Test
    void testGetStoreName() {
        assertEquals("my_store", store.getStoreName());
    }

    @org.junit.jupiter.api.Test
    void testGetProducts() {
        assertEquals(50, store.getProducts().getAllProducts().get(product.getProductId()));
    }

    @org.junit.jupiter.api.Test
    void testUpdateProductQuantity() {
//        assertFalse(store.addToProductQuantity(product, -11));
//        assertTrue(store.addToProductQuantity(product, -9));
        assertEquals(41, store.getProducts().getAllProducts().get(product.getProductId()));
    }

    @org.junit.jupiter.api.Test
    void testAddPurchase() {
   //     Purchase purchase = new Purchase();
      //  store.addPurchase(purchase);
        //assertEquals(1, store.getPurchaseList().size());
    }

    @org.junit.jupiter.api.Test
    void testAddProduct() throws Exception {
        assertThrows(Exception.class, () -> store.addProduct("bratz", 60.9, "toy", 7,"vv"));
        assertEquals("bratz", store.getProducts().getAllProducts().stream().filter(p -> p.getProductId() == product.getProductId()).findFirst().orElse(null).getProductName());
    }

    @org.junit.jupiter.api.Test
    void testRemoveProduct() throws Exception {
        int productId = product.getProductId();
        store.removeProduct(productId);
        assertNull(store.getProducts().getAllProducts().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null));
    }

    @org.junit.jupiter.api.Test
    void testIsOpen() {
        assertFalse(store.isOpen());
        store.setOpen(true);
        assertTrue(store.isOpen());
    }

    @org.junit.jupiter.api.Test
    void testGetStoreId() {
        assertEquals(1, store.getStoreId());
    }

    @org.junit.jupiter.api.Test
    void testGetProduct() throws Exception {
        assertEquals(product, store.getProduct(product.getProductId()));
        assertThrows(Exception.class, () -> store.getProduct(0));
    }

    @org.junit.jupiter.api.Test
    void testEditProductName() throws Exception {
        int productId = product.getProductId();
        store.editProductName(productId, "New Name");
        assertEquals("New Name", store.getProducts().getAllProducts().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null).getProductName());
        assertThrows(Exception.class, () -> store.editProductName(0, "Invalid Product"));
        assertThrows(Exception.class, () -> store.editProductName(productId, "barbi"));
    }


    @org.junit.jupiter.api.Test
    void testEditProductPrice() throws Exception {
        int productId = product.getProductId();
        store.editProductPrice(productId, 20);
        assertEquals(19.99, store.getProducts().getAllProducts().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null).getPrice());
        assertThrows(Exception.class, () -> store.editProductPrice(0, 20));

    }

    @org.junit.jupiter.api.Test
    public void testEditProductCategory() throws Exception {
        store.editProductCategory(product.getProductId(), "New Category");
        assertEquals("New Category", store.getProduct(product.getProductId()).getCategory());
    }




}

