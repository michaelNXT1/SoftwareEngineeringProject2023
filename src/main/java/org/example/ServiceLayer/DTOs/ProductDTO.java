package org.example.ServiceLayer.DTOs;


import org.example.BusinessLayer.Product;

public class ProductDTO {
    private int productId;
    private String productName;
    private double price;
    private String category;
    private double rating;
    private int amount;
    private long description;

    public ProductDTO(int productId, String productName, double price, String category, double rating, int amount, long description) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.rating = rating;
        this.amount = amount;
        this.description = description;
    }

    public static ProductDTO FromProductToProductDTO(Product p){
        return new ProductDTO(p.getProductId(),p.getProductName(),p.getProductPrice(),p.getCategory(),p.getRating(),p.getAmount(),p.getDescription());
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getProductPrice() {
        return price;
    }

    public long getDescription() {
        return description;
    }

    public void setDescription(long newDescription) {
        this.description = newDescription;
    }
}
