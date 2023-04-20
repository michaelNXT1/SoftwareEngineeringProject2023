package org.example.BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Purchase purchaseShoppingCart() {
        List<PurchaseProduct> totalProducts = new ArrayList<>();
        for (ShoppingBag sb : shoppingBags) {
            for (Map.Entry<Product, Integer> e : sb.getProductList().entrySet()) {
                PurchaseProduct pp = sb.purchaseProduct(e.getKey());
                if (pp != null)
                    totalProducts.add(pp);
            }
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
