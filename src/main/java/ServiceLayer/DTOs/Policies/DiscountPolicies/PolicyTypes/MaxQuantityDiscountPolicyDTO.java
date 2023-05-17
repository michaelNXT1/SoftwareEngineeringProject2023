package ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MaxQuantityDiscountPolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;

import java.util.Map;

public class MaxQuantityDiscountPolicyDTO extends BaseDiscountPolicyDTO {
    private final int productId;
    private final int minQuantity;
    private final boolean allowNone;

    public MaxQuantityDiscountPolicyDTO(MaxQuantityDiscountPolicy maxQuantityDiscountPolicy) throws Exception {
        super(maxQuantityDiscountPolicy);
        this.productId = maxQuantityDiscountPolicy.getProductId();
        this.minQuantity = maxQuantityDiscountPolicy.getMinQuantity();
        this.allowNone = maxQuantityDiscountPolicy.isAllowNone();
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
