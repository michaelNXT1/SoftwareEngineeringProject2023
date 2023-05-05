package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Product;

import java.util.Map;

public class MaxQuantityPolicy extends PurchasePolicy {
    private final int productId;
    private final int maxQuantity;
    private final boolean allowNone;

    public MaxQuantityPolicy(int policyId, int productId, int maxQuantity, boolean allowNone) {
        super(policyId);
        this.productId = productId;
        this.maxQuantity = maxQuantity;
        this.allowNone = allowNone;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p.getProductId() == productId)
                return productList.get(p) <= maxQuantity;
        return allowNone;
    }
}
