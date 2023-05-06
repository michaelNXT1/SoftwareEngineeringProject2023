package BusinessLayer.Discounts;

import BusinessLayer.Product;

abstract public class Discount {
    protected int discountId;
    protected double discountPercentage;

    public Discount(int discountId, double discountPercentage) throws Exception {
        this.discountId = discountId;
        if (discountPercentage <= 0.0 || 1.0 < discountPercentage)
            throw new Exception("discount percentage illegal");
        this.discountPercentage = discountPercentage;
    }
}
