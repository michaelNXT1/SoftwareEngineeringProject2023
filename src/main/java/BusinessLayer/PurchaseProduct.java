package BusinessLayer;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
import jakarta.persistence.*;


@Entity
@Table(name = "Purchase_products")
public class PurchaseProduct {
    @Column(name="purchase_id")
    private int purchaseId;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "store_id")
    private int storeId;
    @Column(name = "product_name", columnDefinition = "text")
    private String productName;
    @Column(name = "product_category", columnDefinition = "text")
    private String productCategory;
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private int quantity;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public PurchaseProduct(Product p, int quantity, int storeId, int purchaseId) {
        this.productId = p.getProductId();
        this.storeId = storeId;
        this.productName = p.getProductName();
        this.productCategory = p.getCategory();
        this.price = p.getPrice();
        this.quantity = quantity;
        this.purchaseId=purchaseId;
    }

    public PurchaseProduct(int productId, int storeId, String productName, String productCategory, double price, int quantity) {
        this.productId = productId;
        this.storeId = storeId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.price = price;
        this.quantity = quantity;
    }

    public PurchaseProduct() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
