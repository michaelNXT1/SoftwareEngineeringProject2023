package BusinessLayer.Discounts;

import BusinessLayer.Product;

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
}
