package org.example.BusinessLayer;

public class Product {
        private int productId;
        private String productName;
        private double price;
        private String category;
        private double rating;
        private int amount;
        private String description;

        private  Discount discount;

        private PurchaseType purchaseType;

    public Product(int productId, String productName, double price, String category,String description) throws Exception{
        if (price < 0)
            throw new Exception("cannot add product with negative price");
        if(stringIsEmpty(productName))
            throw new Exception("product name is empty");
        if(stringIsEmpty(category))
            throw new Exception("product category is empty");
        this.discount = new VisibleDiscount(this);
        this.purchaseType = new BuyItNow(this);
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.rating = 0;
        this.description = description;
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

    public void setProductName(String productName) throws Exception {
        if(stringIsEmpty(productName))
            throw new Exception("product name is empty");
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws Exception {
        if (price < 0)
            throw new Exception("cannot set product with negative price");
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) throws Exception {
        if(stringIsEmpty(category))
            throw new Exception("product category is empty");
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

    private static boolean stringIsEmpty(String value) {
        return value == null || value.equals("");
    }
}


