package BusinessLayer.Discounts;

import BusinessLayer.Product;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Discounts.ProductDiscountDTO;

public class ProductDiscount extends Discount {
    private final int productId;

    public ProductDiscount(int discountId, double discountPercentage, int productId, int compositionType) throws Exception {
        super(discountId, discountPercentage, compositionType);
        this.productId = productId;
    }

    @Override
    public boolean checkApplies(Product p) {
        return p.getProductId() == productId;
    }

    @Override
    public DiscountDTO copyConstruct() {
        return new ProductDiscountDTO(this);
    }
}
