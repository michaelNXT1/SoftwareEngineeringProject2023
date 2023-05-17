package BusinessLayer.Discounts;

import BusinessLayer.Product;
import ServiceLayer.DTOs.Discounts.CategoryDiscountDTO;
import ServiceLayer.DTOs.Discounts.DiscountDTO;

public class CategoryDiscount extends Discount {
    private final String category;

    public CategoryDiscount(int discountId, double discountPercentage, String category, int compositionType) throws Exception {
        super(discountId, discountPercentage, compositionType);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean checkApplies(Product p) {
        return p.getCategory().equals(category);
    }

    @Override
    public DiscountDTO copyConstruct() {
        return new CategoryDiscountDTO(this);
    }
}
