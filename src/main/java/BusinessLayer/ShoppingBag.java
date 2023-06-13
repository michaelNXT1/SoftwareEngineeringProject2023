package BusinessLayer;

import Utils.Pair;

import java.util.*;

import static org.atmosphere.annotation.AnnotationUtil.logger;

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
        if (quantity == 0)
            productList.remove(productId);
        else if (store.getProducts().get(p) >= quantity)
            productList.put(productId, quantity);
        else {
            logger.error("Requested product quantity not available.");
            throw new Exception("Requested product quantity not available.");
        }
    }

    //Use case 2.13
    public void removeProduct(int productId) throws Exception {
        store.getProduct(productId);
        productList.remove(productId);
    }

    //Use case 2.14
    public Pair<PurchaseProduct, Boolean> purchaseProduct(int productId) {
        try {
            PurchaseProduct pp = store.subtractForPurchase(productId, productList.get(productId));
            double discountPercentage = store.getProductDiscountPercentage(productId, productList);
            pp.setPrice(pp.getPrice() * (1.0 - discountPercentage));
            return new Pair<>(pp, true);
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    public Pair<List<PurchaseProduct>, Boolean> purchaseShoppingBag() throws Exception {
        if (!store.checkPoliciesFulfilled(productList)) {
            logger.error("Store purchase policies are not fulfilled in this cart");
            throw new Exception("Store purchase policies are not fulfilled in this cart");
        }
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
        productList.clear();
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

    public double getProductDiscountPercentageInCart(int productId) throws Exception {
        return store.getProductDiscountPercentage(productId, productList);
    }
}
