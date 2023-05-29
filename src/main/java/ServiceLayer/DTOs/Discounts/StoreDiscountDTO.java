package ServiceLayer.DTOs.Discounts;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Discounts.StoreDiscount;
import ServiceLayer.DTOs.StoreDTO;

public class StoreDiscountDTO extends DiscountDTO {
    private final StoreDTO store;

    public StoreDiscountDTO(StoreDiscount discount) {
        super(discount);
        this.store = new StoreDTO(discount.getStore());
    }
}
