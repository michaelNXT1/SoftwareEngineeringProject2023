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
    public void addProduct(Product p, int quantity) {
        if (store.getProducts().get(p) >= quantity)
            productList.put(p, quantity);
    }

    //Use case 2.12
    public void changeProductQuantity(Product p, int newQuantity) {
        if (productList.containsKey(p))
            addProduct(p, newQuantity);
    }

    //Use case 2.13
    public void removeProduct(Product p) {
        if (productList.containsKey(p))
            productList.remove(p);
    }

    //Use case 2.14
    public boolean purchaseProduct(Product p) {
        //TODO: lock store
        if (!store.updateProductQuantity(p, -1 * productList.get(p)))
            return false;
        bagPurchase.addProduct(new PurchaseProduct(p, productList.get(p)));
        //TODO: release store
        productList.remove(p);
        return true;
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
