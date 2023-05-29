package ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MinBagTotalDiscountPolicy;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;

public class MinBagTotalDiscountPolicyDTO extends BaseDiscountPolicyDTO {
    private final double minTotal;

    public MinBagTotalDiscountPolicyDTO(MinBagTotalDiscountPolicy minBagTotalDiscountPolicy) throws Exception {
        super(minBagTotalDiscountPolicy);
        this.minTotal = minBagTotalDiscountPolicy.getMinTotal();
    }

    public double getMinTotal() {
        return minTotal;
    }

    @Override
    public String toString() {
        return "Min bag total is " + minTotal;
    }
}
