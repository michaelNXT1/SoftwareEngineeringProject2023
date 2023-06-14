package ServiceLayer.DTOs;

import BusinessLayer.Purchase;
import BusinessLayer.PurchaseProduct;

public class PurchaseProductDTO {
    private String productName;
    private String productCategory;
    private double price;
    private int quantity;

    public PurchaseProductDTO(PurchaseProduct p) {
        this.productName = p.getProductName();
        this.productCategory = p.getProductCategory();
        this.price = p.getPrice();
        this.quantity = p.getQuantity();
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return this.productName;
    }
}
