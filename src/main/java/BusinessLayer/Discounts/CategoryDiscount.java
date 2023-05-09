package BusinessLayer.Discounts;

import BusinessLayer.Product;

public class CategoryDiscount extends Discount {
    private final String category;

    public CategoryDiscount(int discountId, double discountPercentage, String category, CompositionType compositionType) throws Exception {
        super(discountId, discountPercentage, compositionType);
        this.category = category;
    }

    @Override
    public boolean checkApplies(Product p) {
        return p.getCategory().equals(category);
    }
}
