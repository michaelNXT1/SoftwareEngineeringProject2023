package BusinessLayer.Repositories.Interfaces;

import BusinessLayer.PurchaseProduct;

import java.util.List;

public interface IPurchaseProductRepository {
    void addPurchaseProduct(PurchaseProduct purchaseProduct);
    void removePurchaseProduct(PurchaseProduct purchaseProduct);
    List<PurchaseProduct> getAllPurchaseProducts();
}
