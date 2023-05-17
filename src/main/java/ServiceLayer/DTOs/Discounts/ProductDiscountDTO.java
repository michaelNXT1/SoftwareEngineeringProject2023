package ServiceLayer.DTOs.Discounts;

import BusinessLayer.Discounts.ProductDiscount;
import ServiceLayer.DTOs.Discounts.DiscountDTO;

public class ProductDiscountDTO extends DiscountDTO {
    private final int productId;

    public ProductDiscountDTO(ProductDiscount productDiscount) {
        super(productDiscount);
        this.productId = productDiscount.getDiscountId();
    }

    public int getProductId() {
        return productId;
    }
}
