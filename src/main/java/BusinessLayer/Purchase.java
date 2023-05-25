package BusinessLayer;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "bagPurchase", cascade = CascadeType.ALL)
    private List<PurchaseProduct> productList;
    private PaymentDetails paymentDetails;

    public Purchase(List<PurchaseProduct> productList) {
        this.id = 0L; // Initializing with a default value
        this.productList = productList;
    }

    public void addProduct(PurchaseProduct p) {
        productList.add(p);
    }

    public double getTotalPrice() {
        return productList.stream().mapToDouble(PurchaseProduct::getPrice).sum();
    }
}
