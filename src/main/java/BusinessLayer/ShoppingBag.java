package BusinessLayer;

import Utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingBag {
    private final Store store;
    private final Map<Integer, Integer> productList;
    private final Purchase bagPurchase;

    public ShoppingBag(Store store) {
        this.store = store;
        this.productList = new HashMap<>();
        bagPurchase = new Purchase(new ArrayList<>());
    }

    //Use case 2.10 & Use case 2.12
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
    public Pair<PurchaseProduct, Boolean> purchaseProduct(int productId) {
        try {
            //TODO: lock store
            PurchaseProduct pp = store.subtractForPurchase(productId, productList.get(productId));
            //TODO: release store
            productList.remove(productId);
            return new Pair<>(pp, true);
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    public Pair<List<PurchaseProduct>, Boolean> purchaseShoppingBag() throws Exception {
        if (!store.checkPoliciesFulfilled(productList))
            throw new Exception("Store purchase policies are not fulfilled in this cart");
        store.checkPoliciesFulfilled(productList);
        List<PurchaseProduct> retList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : productList.entrySet()) {
            Pair<PurchaseProduct, Boolean> ppp = purchaseProduct(e.getKey());
            if (ppp.getSecond()) {
                retList.add(ppp.getFirst());
                bagPurchase.addProduct(ppp.getFirst());
            } else {
                revertPurchase(retList);
                return new Pair<>(null, false);
            }
        }
        store.addPurchase(bagPurchase);
        return new Pair<>(retList, true);
    }

    public void revertPurchase(List<PurchaseProduct> retList) throws Exception {
        for (PurchaseProduct p : retList)
            store.addToProductQuantity(p.getProductId(), p.getQuantity());
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
