package BusinessLayer.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.PurchasePolicy;
import BusinessLayer.Product;

import java.util.Map;

public class MaxQuantityPolicy extends PurchasePolicy {
    private final int productId;
    private final int maxQuantity;
    private final boolean allowNone;

    public MaxQuantityPolicy(int policyId, int productId, int maxQuantity, boolean allowNone) throws Exception {
        super(policyId);
        if (maxQuantity <= 0) {
            logger.error("Max quantity must be larger than 0 but is" + maxQuantity);
            throw new Exception("Max quantity must be larger than 0");
        }
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
