package BusinessLayer;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchases")
public class Purchase {
    private LocalDateTime purchaseDateTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Transient
    private List<PurchaseProduct> productList = new ArrayList<>();
    public Purchase(List<PurchaseProduct> productList, String username) {
        this.productList = productList;
        this.purchaseDateTime = LocalDateTime.now();
        this.username= username;
    }


    public Purchase() {
    }

    public void addProduct(PurchaseProduct p) {
        productList.add(p);
    }

    public double getTotalPrice() {
        List<PurchaseProduct> purchaseProducts = productList;
        return purchaseProducts.stream().mapToDouble(PurchaseProduct::getPrice).sum();
    }


    public List<PurchaseProduct> getProductList() {
        return this.productList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setProductList(List<PurchaseProduct> productList) {
        this.productList = productList;
    }


    public LocalDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }
}
