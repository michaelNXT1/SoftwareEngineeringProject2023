package BusinessLayer;

import DAOs.BidDAO;
import Repositories.IBidRepository;
import ServiceLayer.DTOs.ProductDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import static org.atmosphere.annotation.AnnotationUtil.logger;
//import javax.persistence.*;


@Entity
@Table(name = "products")
public class Product {

    public enum PurchaseType {
        BUY_IT_NOW,
        OFFER,
        AUCTION
    }

    @Column(name = "store_id")
    private int storeId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name", columnDefinition = "text")
    private String productName;

    @Column(name = "price")
    private double price;

    @Column(name = "category", columnDefinition = "text")
    private String category;

    @Column(name = "rating")
    private double rating;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    private PurchaseType purchaseType;

    @Transient
    private IBidRepository bidRepository = new BidDAO();

    @Column(name="auction_end_time")
    private LocalDateTime auctionEndTime;


    public Product(int storeId, int productId, String productName, double price, String category, int quantity, String description, ProductDTO.PurchaseType purchaseType) throws Exception {
        if (stringIsEmpty(productName)) {
            logger.error("product name is empty");
            throw new Exception("product name is empty");
        }
        if (price < 0) {
            logger.error("cannot add product with negative price");
            throw new Exception("cannot add product with negative price");
        }
        if (stringIsEmpty(category)) {
            logger.error("product category is empty");
            throw new Exception("product category is empty");
        }
        if (quantity < 0) {
            logger.error("cannot add product with negative quantity");
            throw new Exception("cannot add product with negative quantity");
        }
        this.storeId = storeId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.description = description;
        this.purchaseType = PurchaseType.valueOf(purchaseType.name());
        this.rating = 0;
        this.auctionEndTime=null;
    }

    public Product(int storeId, int andIncrement, String productName, Double price, String category, Integer quantity, String description, ProductDTO.PurchaseType purchaseType, LocalDateTime auctionEndDateTime) throws Exception {
        this(storeId, andIncrement, productName, price, category, quantity, description, purchaseType);
        this.auctionEndTime=auctionEndDateTime;
    }

    public Product() {

    }

    private static boolean stringIsEmpty(String value) {
        return value == null || value.equals("");
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getStoreId() {
        return storeId;
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
        if (stringIsEmpty(productName))
            throw new Exception("product name is empty");
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws Exception {
        if (price < 0) {
            logger.error("cannot set product with negative price");
            throw new Exception("cannot set product with negative price");
        }
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) throws Exception {
        if (stringIsEmpty(category)) {
            logger.error("product category is empty");
            throw new Exception("product category is empty");
        }
        this.category = category;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int amount) {
        this.quantity = amount;
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

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }
}


