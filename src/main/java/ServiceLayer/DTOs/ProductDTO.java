package ServiceLayer.DTOs;


import BusinessLayer.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductDTO {
    public enum PurchaseType {
        BUY_IT_NOW,
        OFFER,
        AUCTION
    }

    public static Map<String, ProductDTO.PurchaseType> stringToPermMap = new HashMap<>();
    public static Map<ProductDTO.PurchaseType, String> permToStringMap = new HashMap<>();

    static {
        stringToPermMap.put("Buy it Now", PurchaseType.BUY_IT_NOW);
        stringToPermMap.put("Make Offer", PurchaseType.OFFER);
        stringToPermMap.put("Auction", PurchaseType.AUCTION);
        permToStringMap.put(PurchaseType.BUY_IT_NOW, "Buy it Now");
        permToStringMap.put(PurchaseType.OFFER, "Make Offer");
        permToStringMap.put(PurchaseType.AUCTION, "Auction");
    }

    private final int storeId;
    private int productId;
    private String productName;
    private double price;
    private String category;
    private double rating;
    private int amount;
    private String description;
    private PurchaseType purchaseType;

    public ProductDTO(Product p) {
        this.storeId = p.getStoreId();
        this.productId = p.getProductId();
        this.productName = p.getProductName();
        this.price = p.getProductPrice();
        this.category = p.getCategory();
        this.rating = p.getRating();
        this.amount = p.getQuantity();
        this.description = p.getDescription();
        switch (p.getPurchaseType()) {
            case BUY_IT_NOW -> this.purchaseType = PurchaseType.BUY_IT_NOW;
            case OFFER -> this.purchaseType = PurchaseType.OFFER;
            case AUCTION -> this.purchaseType = PurchaseType.AUCTION;
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public int getStoreId() {
        return storeId;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    @Override
    public String toString() {
        return String.format(this.productName);
    }
}
