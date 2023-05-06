package BusinessLayer.Discounts;

import BusinessLayer.Product;

public class ProductDiscount extends Discount {
    private int productId;

    public ProductDiscount(int discountId, double discountPercentage, int productId) throws Exception {
        super(discountId, discountPercentage);
        this.productId = productId;
    }
}
