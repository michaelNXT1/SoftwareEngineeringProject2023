package ServiceLayer.DTOs.Policies.DiscountPolicies;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;

public class BaseDiscountPolicyDTO {
    protected int policyId;

    public BaseDiscountPolicyDTO(BaseDiscountPolicy baseDiscountPolicy) {
        this.policyId = baseDiscountPolicy.getPolicyId();
    }

    public int getPolicyId() {
        return policyId;
    }
}
