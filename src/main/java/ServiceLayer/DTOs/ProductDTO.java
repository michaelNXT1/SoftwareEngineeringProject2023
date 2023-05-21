package ServiceLayer.DTOs;


import BusinessLayer.Product;
import BusinessLayer.PurchaseType;
import DataAccessLayer.DAOs.ProductDAO;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;
    @Column(name = "store_id")
    private final int storeId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "price")
    private double price;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "rating")
    private double rating;
    @Column(name = "amount")
    private int amount;
    @Column(name = "description")
    private String description;
    @Column(name = "purchaseType")
    private String purchaseType;

    private ProductDAO productDAO;


    private String mapPurchaseTypeToString(PurchaseType purchaseType){
        String className = purchaseType.getClass().getName();
        return className;
    }
    public ProductDTO(Product p) {
        this.storeId = p.getStoreId();
        this.productId = p.getProductId();
        this.productName = p.getProductName();
        this.price = p.getProductPrice();
        this.category = p.getCategory();
        this.rating = p.getRating();
        this.amount = p.getAmount();
        this.description = p.getDescription();
        PurchaseType purchaseType = p.getPurchaseType();
        this.purchaseType = mapPurchaseTypeToString(purchaseType);
        this.productDAO = new ProductDAO();
        productDAO.addProduct(this);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
        productDAO.updateProduct(this);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        productDAO.updateProduct(this);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        productDAO.updateProduct(this);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        productDAO.updateProduct(this);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
        productDAO.updateProduct(this);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        productDAO.updateProduct(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
        productDAO.updateProduct(this);
    }

    public int getStoreId() {
        return storeId;
    }

    @Override
    public String toString() {
        return String.format(this.productName);
    }
}
