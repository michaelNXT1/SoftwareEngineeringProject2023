package BusinessLayer;
import DAOs.PurchaseProductDAO;
import Repositories.IProductRepository;
import Repositories.IPurchaseProductRepository;

//import javax.persistence.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchases")
public class Purchase {
    private LocalDateTime purchaseDateTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private IPurchaseProductRepository productList;
    public enum PurchaseType {
        BuyItNow,
        Raffle,
        Auction,
        Offer
    }
    @Enumerated(EnumType.STRING)
    protected PurchaseType type;
    public Purchase(IPurchaseProductRepository productList) {
        this.productList = productList;
        this.purchaseDateTime = LocalDateTime.now();
        type = PurchaseType.BuyItNow;
    }


    public Purchase() {
        type = PurchaseType.BuyItNow;
    }

    public void addProduct(PurchaseProduct p) {
        productList.addPurchaseProduct(p);
    }

    public double getTotalPrice() {
        List<PurchaseProduct> purchaseProducts = productList.getAllPurchaseProducts();
        return purchaseProducts.stream().mapToDouble(PurchaseProduct::getPrice).sum();
    }


    public IPurchaseProductRepository getProductList() {
        return this.productList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setProductList(IPurchaseProductRepository productList) {
        this.productList = productList;
    }


    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }
}
