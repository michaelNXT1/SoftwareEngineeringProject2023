package ServiceLayer.DTOs.Discounts;

import BusinessLayer.Discounts.Discount;

public abstract class DiscountDTO {
    protected final int discountId;
    protected final double discountPercentage;

    public DiscountDTO(Discount discount) {
        this.discountId = discount.getDiscountId();
        this.discountPercentage = discount.getDiscountPercentage();
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public int getDiscountId() {
        return discountId;
    }
}
