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
    public void addProduct(Product p, int quantity, Store s) {
        //TODO: add purchase type check
        shoppingBagExists(s);
        for (ShoppingBag sb : shoppingBags)
            if (sb.getStore().equals(this))
                sb.addProduct(p, quantity);
    }

    //Use case 2.12
    public void changeProductQuantity(Product p, int newQuantity, Store s) {
        if (shoppingBags.stream().anyMatch(sb -> sb.getStore().equals(s)))
            for (ShoppingBag sb : shoppingBags)
                if (sb.getStore().equals(this))
                    sb.changeProductQuantity(p, newQuantity);
    }

    //Use case 2.13
    public void removeProduct(Product p, Store s) {
        if (shoppingBags.stream().anyMatch(sb -> sb.getStore().equals(s)))
            for (ShoppingBag sb : shoppingBags)
                if (sb.getStore().equals(this))
                    sb.removeProduct(p);
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

    private void shoppingBagExists(Store s) {
        if (!shoppingBags.stream().anyMatch(sb -> sb.getStore().equals(s)))
            shoppingBags.add(new ShoppingBag(this, s));
    }
}
