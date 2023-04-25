package org.example.BusinessLayer;

import org.example.Utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {
    public List<ShoppingBag> shoppingBags;

    public ShoppingCart() {
        shoppingBags = new ArrayList<>();
    }

    //Use case 2.10
    //Use case 2.12
    public void setProductQuantity(Store s, int productId, int quantity) throws Exception {
        //TODO: add purchase type check
        getShoppingBag(s).setProductQuantity(productId, quantity);
    }

    //Use case 2.13
    public void removeProduct(Store s, int productId) throws Exception {
        ShoppingBag shoppingBag = shoppingBags.stream().filter(sb -> sb.getStore().equals(s)).findFirst().orElse(null);
        if (shoppingBag == null)
            throw new Exception("store doesn't exist in cart");
        shoppingBag.removeProduct(productId);
    }

    //Use case 2.14
    public Purchase purchaseShoppingCart() throws Exception {
        if (isEmpty())
            throw new Exception("Purchase failed, cart is empty");
        Map<ShoppingBag, List<PurchaseProduct>> shoppingBagMap = new HashMap<>();
        for (ShoppingBag sb : shoppingBags) {
            Pair<List<PurchaseProduct>, Boolean> sbp = sb.purchaseShoppingBag();
            if (sbp.getSecond()) {
                shoppingBagMap.put(sb, sbp.getFirst());
            } else {
                for (Map.Entry<ShoppingBag, List<PurchaseProduct>> entry : shoppingBagMap.entrySet())
                    entry.getKey().revertPurchase(entry.getValue());
                throw new Exception("Purchase failed, not all products were in stock as requested.");
            }
        }
        shoppingBags.clear();
        List<PurchaseProduct> flattenedList = shoppingBagMap.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return new Purchase(flattenedList);
    }

    private ShoppingBag getShoppingBag(Store s) {
        return shoppingBags.stream()
                .filter(sb -> sb.getStore().equals(s))
                .findFirst()
                .orElseGet(() -> {
                    ShoppingBag newBag = new ShoppingBag(s);
                    shoppingBags.add(newBag);
                    return newBag;
                });
    }

    private boolean isEmpty() {
        return shoppingBags.stream().allMatch(ShoppingBag::isEmpty);
    }
}
