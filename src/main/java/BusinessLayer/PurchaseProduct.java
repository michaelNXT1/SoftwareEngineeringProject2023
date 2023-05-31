package BusinessLayer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Purchase_products")
public class PurchaseProduct {
    @Id
    @Column(name = "product_id")
    private int productId;
    @Column(name = "store_id")
    private int storeId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_category")
    private String productCategory;
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
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
