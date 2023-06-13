package BusinessLayer;
//import javax.persistence.*;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract public class PurchaseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(mappedBy = "purchaseType")
    protected Product product;

    public PurchaseType(Product product) {
        this.product = product;
    }

    public PurchaseType() {

    }
}
