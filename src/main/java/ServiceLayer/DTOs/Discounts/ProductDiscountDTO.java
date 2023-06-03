package ServiceLayer.DTOs.Discounts;

import BusinessLayer.Discounts.ProductDiscount;

public class ProductDiscountDTO extends DiscountDTO {
    private final int productId;

    public ProductDiscountDTO(ProductDiscount productDiscount) {
        super(productDiscount);
        this.productId = productDiscount.getProductId();
    }

    public int getProductId() {
        return productId;
    }
}
