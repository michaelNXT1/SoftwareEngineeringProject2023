package BusinessLayer;
//import javax.persistence.*;
import BusinessLayer.Product;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class PurchaseType {
    public enum Type {
        BuyItNow,
        Raffle,
        Auction,
        Offer
    }
    @Id
    @Enumerated(EnumType.STRING)
    protected Type type;

    public PurchaseType() {

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
