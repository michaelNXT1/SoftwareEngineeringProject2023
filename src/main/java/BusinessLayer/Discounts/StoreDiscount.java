package BusinessLayer.Discounts;

import BusinessLayer.Product;
import BusinessLayer.Store;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Discounts.StoreDiscountDTO;
//import javax.persistence.*;
import jakarta.persistence.*;


@Entity
@Table(name = "store_discounts")
@DiscriminatorValue("CHILD")
public class StoreDiscount extends Discount {
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public StoreDiscount(double discountPercentage, Store store, int compositionType) throws Exception {
        super(discountPercentage, compositionType);
        this.store = store;
    }

    public StoreDiscount() {
        this.store = null;
    }

    public Store getStore() {
        return store;
    }

    @Override
    public boolean checkApplies(Product p) {
        return store.getProducts().getAllProducts().contains(p);
    }

    @Override
    public DiscountDTO copyConstruct() {
        return new StoreDiscountDTO(this);
    }
}
