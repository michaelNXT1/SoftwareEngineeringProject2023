package ServiceLayer.DTOs;

import BusinessLayer.Purchase;
import BusinessLayer.PurchaseProduct;

public class PurchaseProductDTO {
    private String productName;
    private String productCategory;
    private double price;
    private int quantity;

    public PurchaseProductDTO(String productName, String productCategory, double price, int quantity) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.price = price;
        this.quantity = quantity;
    }

    public static PurchaseProductDTO fromPurchaseToPurchaseDTO(PurchaseProduct p){
        return new PurchaseProductDTO(p.getProductName(),p.getProductCategory(),p.getPrice(),p.getQuantity());
    }
}
