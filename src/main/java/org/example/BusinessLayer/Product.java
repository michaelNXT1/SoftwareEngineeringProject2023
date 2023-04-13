package org.example.BusinessLayer;

public class Product {
        private int productid;
        private String productName;
        private double price;
        private String category;
        private double rating;
        private int amount;

        private  Discount discount;

        private PurchaseType purchaseType;

    public Product(int productid, String productName, double price, String category, double rating, int amount) {
        this.discount = new VisibleDiscount(this);
        this.purchaseType = new BuyItNow(this);
        this.productid = productid;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.rating = rating;
        this.amount = amount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    }


