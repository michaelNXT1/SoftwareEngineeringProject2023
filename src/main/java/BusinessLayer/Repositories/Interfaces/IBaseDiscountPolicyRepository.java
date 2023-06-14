package BusinessLayer.Repositories.Interfaces;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;

import java.util.List;

public interface IBaseDiscountPolicyRepository {
    void addDiscountPolicy(BaseDiscountPolicy discountPolicy);
    void removeDiscountPolicy(BaseDiscountPolicy discountPolicy);
    List<BaseDiscountPolicy> getAllDiscountPolicies();
}
