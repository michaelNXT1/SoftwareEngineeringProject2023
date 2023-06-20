package BusinessLayer;

import DAOs.OfferApprovalDAO;
import Repositories.IOfferApprovalRepository;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offerId;
    @ManyToOne
    @JoinColumn(name = "username")
    private Member offeringUser;
    @Column(name = "store_id")
    private int storeId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;
    @Column(name = "price_per_item")
    private double pricePerItem;
    @Column(name = "quantity")
    private int quantity;
    @Transient
    private IOfferApprovalRepository offerApprovalRepository = new OfferApprovalDAO();

    public Offer(Member offeringUser, int storeId, Product productId, double pricePerItem, int quantity) {
        this.offeringUser = offeringUser;
        this.storeId = storeId;
        this.productId = productId;
        this.pricePerItem = pricePerItem;
        this.quantity = quantity;
    }

    public Offer() {
    }

    public void addOfferApproval(List<Member> approvingMembers){
        for (Member m : approvingMembers) {
            OfferApproval offerApproval = new OfferApproval(offerId, m, -1);
            offerApprovalRepository.saveOfferApproval(offerApproval);
        }
    }

    public int getOfferId() {
        return offerId;
    }

    public Member getOfferingUser() {
        return offeringUser;
    }

    public Product getProductId() {
        return productId;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStoreId() {
        return storeId;
    }

    public IOfferApprovalRepository getOfferApprovalRepository() {
        return offerApprovalRepository;
    }
}
