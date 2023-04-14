package org.example.BusinessLayer;

import java.util.List;
import java.util.Map;

public class Purchase {
    private List<PurchaseProduct> productList;

    public Purchase(List<PurchaseProduct> productList) {
        this.productList = productList;
    }

    public void addProduct(PurchaseProduct p) {
        productList.add(p);
    }
}
