package org.example.BusinessLayer;

public class PurchaseProduct {
    private int productId;
    private String productName;
    private String productCategory;
    private double price;
    private int quantity;

    public PurchaseProduct(Product p, int quantity) {
        this.productId = p.getProductId();
        this.productName = p.getProductName();
        this.productCategory = p.getCategory();
        this.price = p.getPrice();
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
