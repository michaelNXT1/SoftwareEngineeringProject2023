package Repositories;

import BusinessLayer.PurchaseProduct;

import java.util.List;

public interface IPurchaseProductRepository {
    void addPurchaseProduct(PurchaseProduct purchaseProduct);
    void removePurchaseProduct(PurchaseProduct purchaseProduct);
    List<PurchaseProduct> getAllPurchaseProducts();

    void addAllPurchaseProducts(List<PurchaseProduct> purchaseProductList);

    void clear();
}