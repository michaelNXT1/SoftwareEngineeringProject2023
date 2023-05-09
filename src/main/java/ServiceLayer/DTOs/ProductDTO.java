package ServiceLayer.DTOs;


import BusinessLayer.Product;

public class ProductDTO {
    private int productId;
    private String productName;
    private double price;
    private String category;
    private double rating;
    private int amount;
    private String description;

    public ProductDTO(Product p) {
        this.productId = p.getProductId();
        this.productName =  p.getProductName();
        this.price = p.getProductPrice();
        this.category = p.getCategory();
        this.rating = p.getRating();
        this.amount = p.getAmount();
        this.description = p.getDescription();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
    @Override
    public String toString(){
        return String.format(this.productName);
    }
}
