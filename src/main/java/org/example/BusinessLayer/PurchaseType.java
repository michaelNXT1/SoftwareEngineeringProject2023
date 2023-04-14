package org.example.BusinessLayer;

abstract public class PurchaseType {
    protected Product product;

    public PurchaseType(Product product) {
        this.product = product;
    }
}
