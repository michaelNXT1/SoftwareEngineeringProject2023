package BusinessLayer;

import java.util.List;

public class Purchase {
    public List<PurchaseProduct> getProductList() {
        return productList;
    }

    private List<PurchaseProduct> productList;
    private PaymentDetails paymentDetails;

    public Purchase(List<PurchaseProduct> productList) {
        this.productList = productList;
    }

    public void addProduct(PurchaseProduct p) {
        productList.add(p);
    }

    public double getTotalPrice() {
        return productList.stream().mapToDouble(PurchaseProduct::getPrice).sum();
    }
}
