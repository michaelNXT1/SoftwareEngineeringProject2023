package org.example.BusinessLayer;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private String category;
    private double rating;

    private Discount discount;

    private PurchaseType purchaseType;

    public Product(int productId, String productName, double price, String category) {
        this.discount = new VisibleDiscount(this);
        this.purchaseType = new BuyItNow(this);
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.rating = 0;

    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}


