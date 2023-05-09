package BusinessLayer.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Product;

import java.util.Map;

public class MaxQuantityDiscountPolicy extends BaseDiscountPolicy {
    private final int productId;
    private final int minQuantity;
    private final boolean allowNone;

    public MaxQuantityDiscountPolicy(int policyId, int productId, int minQuantity, boolean allowNone) throws Exception {
        super(policyId);
        if (minQuantity <= 0)
            throw new Exception("Max quantity must be larger than 0");
        this.productId = productId;
        this.minQuantity = minQuantity;
        this.allowNone = allowNone;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p.getProductId() == productId)
                return productList.get(p) <= minQuantity;
        return allowNone;
    }
}
