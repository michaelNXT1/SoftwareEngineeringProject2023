package BusinessLayer;

import java.time.LocalDateTime;
import java.util.List;

public class Purchase {
    private static int purchaseIdCounter = 0;
    private final int purchaseId;
    private final LocalDateTime purchaseDateTime;
    private final List<PurchaseProduct> productList;

    public Purchase(List<PurchaseProduct> productList) {
        this.purchaseId = purchaseIdCounter++;
        this.productList = productList;
        this.purchaseDateTime = LocalDateTime.now();
    }

    public List<PurchaseProduct> getProductList() {
        return productList;
    }

    public void addProduct(PurchaseProduct p) {
        productList.add(p);
    }

    public double getTotalPrice() {
        return productList.stream().mapToDouble(PurchaseProduct::getPrice).sum();
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }
}
