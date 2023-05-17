package BusinessLayer.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes.MinQuantityDiscountPolicyDTO;

import java.util.Map;

public class MinQuantityDiscountPolicy extends BaseDiscountPolicy {
    private final int productId;
    private final int minQuantity;
    private final boolean allowNone;

    public MinQuantityDiscountPolicy(int policyId, int productId, int minQuantity, boolean allowNone) throws Exception {
        super(policyId);
        if (minQuantity <= 0) {
            logger.error("Min quantity must be larger than 0 but is " + minQuantity);
            throw new Exception("Min quantity must be larger than 0");
        }
        this.productId = productId;
        this.minQuantity = minQuantity;
        this.allowNone = allowNone;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p.getProductId() == productId)
                return productList.get(p) >= minQuantity;
        return allowNone;
    }

    @Override
    public BaseDiscountPolicyDTO copyConstruct() throws Exception {
        return new MinQuantityDiscountPolicyDTO(this);
    }

    public int getProductId() {
        return productId;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public boolean isAllowNone() {
        return allowNone;
    }
}
