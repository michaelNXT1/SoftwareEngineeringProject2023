package org.example.BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingBag {
    private ShoppingCart shoppingCart;
    private Store store;
    private Map<Product, Integer> productList;
    private Purchase bagPurchase;

    public ShoppingBag(ShoppingCart shoppingCart, Store store) {
        this.shoppingCart = shoppingCart;
        this.store = store;
        this.productList = new HashMap<>();
        bagPurchase = new Purchase(new ArrayList<>());
    }

    //Use case 2.10
    //Use case 2.12
    public void setProductQuantity(int productId, int quantity) throws Exception {
        Product p = store.getProduct(productId);
        if (store.getProducts().get(p) >= quantity)
            productList.put(p, quantity);
    }

    //Use case 2.13
    public void removeProduct(int productId) throws Exception {
        Product p = store.getProduct(productId);
        productList.remove(p);
    }

    //Use case 2.14
    public PurchaseProduct purchaseProduct(Product p) {
        PurchaseProduct pp;
        //TODO: lock store
        if (!store.updateProductQuantity(p, -1 * productList.get(p)))
            return null;
        pp = new PurchaseProduct(p, productList.get(p));
        bagPurchase.addProduct(pp);
        //TODO: release store
        productList.remove(p);
        return pp;
    }

    //Use case 2.14
    public void closePurchase() {
        //TODO: lock store(?)
        store.addPurchase(bagPurchase);
        //TODO: release store(?)
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public Store getStore() {
        return store;
    }

    public Map<Product, Integer> getProductList() {
        return productList;
    }
}
