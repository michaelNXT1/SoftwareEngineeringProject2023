package ServiceLayer.DTOs;

import BusinessLayer.Offer;
import BusinessLayer.OfferApproval;

import java.util.HashMap;
import java.util.Map;

public class OfferDTO {
    private final int offerId;
    private final MemberDTO offeringUser;
    private final ProductDTO product;
    private final double pricePerItem;
    private final int quantity;
    private final Map<MemberDTO, Integer> approvingMembers;

    public OfferDTO(Offer offer) {
        this.offerId = offer.getOfferId();
        this.offeringUser = new MemberDTO(offer.getOfferingUser());
        this.product = new ProductDTO(offer.getProductId());
        this.pricePerItem = offer.getPricePerItem();
        this.quantity = offer.getQuantity();
        approvingMembers = new HashMap<>();
        for (OfferApproval m : offer.getOfferApprovalRepository().getAllOfferApprovals().stream().filter(offerApproval -> offerApproval.getOfferId() == offer.getOfferId()).toList())
            approvingMembers.put(new MemberDTO(m.getEmployee()), m.getResponse());
    }

    public int getOfferId() {
        return offerId;
    }

    public MemberDTO getOfferingUser() {
        return offeringUser;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public Map<MemberDTO, Integer> getApprovingMembers() {
        return approvingMembers;
    }
}
