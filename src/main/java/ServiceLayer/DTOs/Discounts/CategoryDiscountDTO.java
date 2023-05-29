package ServiceLayer.DTOs.Discounts;

import BusinessLayer.Discounts.CategoryDiscount;
import BusinessLayer.Discounts.Discount;

public class CategoryDiscountDTO extends DiscountDTO {
    private final String category;

    public CategoryDiscountDTO(CategoryDiscount discount) {
        super(discount);
        this.category = discount.getCategory();
    }

    public String getCategory() {
        return category;
    }
}
