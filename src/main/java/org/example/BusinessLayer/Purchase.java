package org.example.BusinessLayer;

import java.util.List;
import java.util.Map;

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
}
