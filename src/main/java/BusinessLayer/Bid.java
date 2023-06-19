package BusinessLayer;

import jakarta.persistence.*;

@Entity
@Table(name="bids")
public class Bid {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int bidId;
    @Column(name = "store_id")
    private int storeId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;
    @ManyToOne
    @JoinColumn(name = "username")
    private Member offeringUser;
    @Column(name = "price_per_item")
    private double offeredPrice;

    public Bid(int storeId, Product productId, Member offeringUser, double offeredPrice) {
        this.storeId = storeId;
        this.productId = productId;
        this.offeringUser = offeringUser;
        this.offeredPrice = offeredPrice;
    }

    public Bid() {
    }

    public int getBidId() {
        return bidId;
    }

    public int getStoreId() {
        return storeId;
    }

    public Product getProductId() {
        return productId;
    }

    public Member getOfferingUser() {
        return offeringUser;
    }

    public double getOfferedPrice() {
        return offeredPrice;
    }
}
