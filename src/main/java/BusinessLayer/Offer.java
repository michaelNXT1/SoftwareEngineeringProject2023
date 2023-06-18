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
    @Column(name = "product_id")
    private int productId;
    @Column(name = "price_per_item")
    private double pricePerItem;
    @Column(name = "quantity")
    private int quantity;
    @Transient
    private IOfferApprovalRepository offerApprovalRepository;

    public Offer(Member offeringUser, int storeId, int productId, double pricePerItem, int quantity, List<Member> approvingMembers) {
        offerApprovalRepository = new OfferApprovalDAO();
        this.offeringUser = offeringUser;
        this.storeId = storeId;
        this.productId = productId;
        this.pricePerItem = pricePerItem;
        this.quantity = quantity;
        for (Member m : approvingMembers) {
            OfferApproval offerApproval = new OfferApproval(offerId, m, false);
            offerApprovalRepository.saveOfferApproval(offerApproval);
        }
    }

    public Offer() {
    }

    public void respondToOffer(Member approvingMember, boolean response) {
        OfferApproval offerApproval = offerApprovalRepository.getAllOfferApprovals().stream().filter(oa ->
                oa.getOfferId() == offerId && oa.getEmployee().getUsername().equals(approvingMember.getUsername())).findFirst().orElse(null);
        offerApproval.setResponse(response);
        offerApprovalRepository.saveOfferApproval(offerApproval);
    }
}
