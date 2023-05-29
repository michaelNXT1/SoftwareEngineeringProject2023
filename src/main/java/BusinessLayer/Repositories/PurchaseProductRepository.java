package BusinessLayer.Repositories;

import BusinessLayer.PurchaseProduct;
import BusinessLayer.Repositories.Interfaces.IPurchaseProductRepository;

import java.util.ArrayList;
import java.util.List;

public class PurchaseProductRepository implements IPurchaseProductRepository {
    private final List<PurchaseProduct> productList;

    public PurchaseProductRepository() {
        this.productList = new ArrayList<>();
    }

    @Override
    public void addPurchaseProduct(PurchaseProduct purchaseProduct) {
        productList.add(purchaseProduct);
    }

    @Override
    public void removePurchaseProduct(PurchaseProduct purchaseProduct) {
        productList.remove(purchaseProduct);
    }

    @Override
    public List<PurchaseProduct> getAllPurchaseProducts() {
        return productList;
    }
}
