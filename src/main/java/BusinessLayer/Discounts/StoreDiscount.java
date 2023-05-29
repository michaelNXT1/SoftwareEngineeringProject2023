package BusinessLayer.Discounts;

import BusinessLayer.Product;
import BusinessLayer.Store;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Discounts.StoreDiscountDTO;

public class StoreDiscount extends Discount {
    private final Store store;

    public StoreDiscount(int discountId, double discountPercentage, Store store, int compositionType) throws Exception {
        super(discountId, discountPercentage, compositionType);
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    @Override
    public boolean checkApplies(Product p) {
        return store.getProducts().keySet().contains(p);
    }

    @Override
    public DiscountDTO copyConstruct() {
        return new StoreDiscountDTO(this);
    }
}
