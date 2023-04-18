package org.example.BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    List<ShoppingBag> shoppingBags;
    private int cartTotal;

    public ShoppingCart() {

    }

    //Use case 2.10
    //Use case 2.12
    public void setProductQuantity(Store s, Product p, int quantity) {
        //TODO: add purchase type check
        getShoppingBag(s).setProductQuantity(p, quantity);
    }

    //Use case 2.13
    public void removeProduct(Store s, Product p) {
        shoppingBags.stream().filter(sb -> sb.getStore().equals(s)).forEach(sb -> sb.removeProduct(p));
    }

    //Use case 2.14
    public Purchase purchaseShoppingCart() {
        List<PurchaseProduct> totalProducts = new ArrayList<>();
        for (ShoppingBag sb : shoppingBags) {
            for (Map.Entry<Product, Integer> e : sb.getProductList().entrySet())
                if (sb.purchaseProduct(e.getKey()))
                    totalProducts.add(new PurchaseProduct(e.getKey(), e.getValue()));
            sb.closePurchase();
        }
        shoppingBags.removeIf(sb -> sb.getProductList().isEmpty());
        return new Purchase(totalProducts);
    }

    private ShoppingBag getShoppingBag(Store s) {
        return shoppingBags.stream()
                .filter(sb -> sb.getStore().equals(s))
                .findFirst()
                .orElseGet(() -> {
                    ShoppingBag newBag = new ShoppingBag(this, s);
                    shoppingBags.add(newBag);
                    return newBag;
                });
    }
}
