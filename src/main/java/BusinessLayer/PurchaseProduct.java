package BusinessLayer;

public class PurchaseProduct {
    private int productId;
    private int storeId;
    private String productName;
    private String productCategory;
    private double price;
    private int quantity;

    public PurchaseProduct(Product p, int quantity, int storeId) {
        this.productId = p.getProductId();
        this.storeId = storeId;
        this.productName = p.getProductName();
        this.productCategory = p.getCategory();
        this.price = p.getPrice();
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getStoreId() {
        return storeId;
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

    public void setPrice(double price) {
        this.price = price;
    }
}
