package ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MinQuantityDiscountPolicy;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;

public class MinQuantityDiscountPolicyDTO extends BaseDiscountPolicyDTO {
    private final int productId;
    private final int minQuantity;
    private final boolean allowNone;

    public MinQuantityDiscountPolicyDTO(MinQuantityDiscountPolicy minQuantityDiscountPolicy) {
        super(minQuantityDiscountPolicy);
        this.productId = minQuantityDiscountPolicy.getProductId();
        this.minQuantity = minQuantityDiscountPolicy.getMinQuantity();
        this.allowNone = minQuantityDiscountPolicy.isAllowNone();
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
