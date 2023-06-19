package ServiceLayer.DTOs;

import BusinessLayer.Bid;

public class BidDTO {
    private int bidId;
    private int storeId;
    private int productId;
    private MemberDTO offeringUser;
    private double offeredPrice;

    public BidDTO(Bid bid) {
        this.bidId=bid.getBidId();
        this.storeId=bid.getStoreId();
        this.productId=bid.getProductId().getProductId();
        this.offeringUser=new MemberDTO(bid.getOfferingUser());
        this.offeredPrice=bid.getBidId();
    }

    public int getBidId() {
        return bidId;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getProductId() {
        return productId;
    }

    public MemberDTO getOfferingUser() {
        return offeringUser;
    }

    public double getOfferedPrice() {
        return offeredPrice;
    }
}
