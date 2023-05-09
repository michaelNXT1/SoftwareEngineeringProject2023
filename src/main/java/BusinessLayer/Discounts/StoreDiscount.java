package BusinessLayer.Discounts;

import BusinessLayer.Product;
import BusinessLayer.Store;

public class StoreDiscount extends Discount {
    private final Store store;

    public StoreDiscount(int discountId, double discountPercentage, Store store, int compositionType) throws Exception {
        super(discountId, discountPercentage, compositionType);
        this.store = store;
    }

    @Override
    public boolean checkApplies(Product p) {
        return store.getProducts().keySet().contains(p);
    }
}
