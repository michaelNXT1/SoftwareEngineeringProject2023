package BusinessLayer.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes.MaxQuantityDiscountPolicyDTO;

import java.util.Map;

public class MaxQuantityDiscountPolicy extends BaseDiscountPolicy {
    private final Product product;
    private final int minQuantity;
    private final boolean allowNone;

    public MaxQuantityDiscountPolicy(int policyId, Product product, int maxQuantity, boolean allowNone) throws Exception {
        super(policyId);
        if (maxQuantity <= 0) {
            logger.error("Max quantity must be larger than 0 but is " + maxQuantity);
            throw new Exception("Max quantity must be larger than 0");
        }
        this.product = product;
        this.minQuantity = maxQuantity;
        this.allowNone = allowNone;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p == product)
                return productList.get(p) <= minQuantity;
        return allowNone;
    }

    @Override
    public BaseDiscountPolicyDTO copyConstruct() throws Exception {
        return new MaxQuantityDiscountPolicyDTO(this);
    }

    public Product getProduct() {
        return product;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public boolean isAllowNone() {
        return allowNone;
    }
}
