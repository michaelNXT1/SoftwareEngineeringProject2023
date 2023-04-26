package org.example.BusinessLayer;

import org.example.Utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingBag {
    private ShoppingCart shoppingCart;
    private Store store;
    private Map<Integer, Integer> productList;
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
            productList.put(productId, quantity);
        else
            throw new Exception("Requested product quantity not available.");
    }

    //Use case 2.13
    public void removeProduct(int productId) throws Exception {
        store.getProduct(productId);
        productList.remove(productId);
    }

    //Use case 2.14
    public Pair<PurchaseProduct, Boolean> purchaseProduct(int productId) throws Exception {
        PurchaseProduct pp;
        //TODO: lock store
        Product p;
        try {
            p = store.getProduct(productId);
        } catch (Exception e) {
            return new Pair(null, false);
        }
        if (!store.addToProductQuantity(p, -1 * productList.get(productId)))
            return new Pair(null, false);
        pp = new PurchaseProduct(p, productList.get(productId));
        //TODO: release store
        productList.remove(productId);
        return new Pair(pp, true);
    }

    public Pair<List<PurchaseProduct>, Boolean> purchaseShoppingBag() throws Exception {
        List<PurchaseProduct> retList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : productList.entrySet()) {
            Pair<PurchaseProduct, Boolean> ppp = purchaseProduct(e.getKey());
            if (ppp.getSecond()) {
                retList.add(ppp.getFirst());
                bagPurchase.addProduct(ppp.getFirst());
            } else {
                revertPurchase(retList);
                return new Pair(null, false);
            }
        }
        store.addPurchase(bagPurchase);
        return new Pair(retList, true);
    }

    public void revertPurchase(List<PurchaseProduct> retList) throws Exception {
        for (PurchaseProduct p : retList)
            store.addToProductQuantity(store.getProduct(p.getProductId()), p.getQuantity());
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public Store getStore() {
        return store;
    }

    public Map<Integer, Integer> getProductList() {
        return productList;
    }

    public boolean isEmpty() {
        return productList.values().stream().allMatch(integer -> integer == 0);
    }
}
