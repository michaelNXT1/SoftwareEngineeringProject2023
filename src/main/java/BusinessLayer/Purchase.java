package BusinessLayer;
import DAOs.PurchaseProductDAO;
import Repositories.IProductRepository;
import Repositories.IPurchaseProductRepository;

//import javax.persistence.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "shopping_bag_id")
    private ShoppingBag bagPurchase;
    @Transient
    private IPurchaseProductRepository productList;

    public Purchase(IPurchaseProductRepository productList) {
        this.id = 0L; // Initializing with a default value
        this.productList = productList;
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

    public ShoppingBag getBagPurchase() {
        return bagPurchase;
    }

    public void setBagPurchase(ShoppingBag bagPurchase) {
        this.bagPurchase = bagPurchase;
    }

    public void setProductList(IPurchaseProductRepository productList) {
        this.productList = productList;
    }
}
