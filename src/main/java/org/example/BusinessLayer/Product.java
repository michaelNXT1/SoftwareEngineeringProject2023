package org.example.BusinessLayer;

public class Product {
        private int productId;
        private String productName;
        private double price;
        private String category;
        private double rating;
        private int amount;

    public Product(int productId, String productName, double price, String category, double rating, int amount) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.rating = rating;
        this.amount = amount;
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

    public double getRating() { return rating;
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
