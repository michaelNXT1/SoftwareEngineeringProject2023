package Repositories;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;

import java.util.List;

public interface IBaseDiscountPolicyRepository {
    void addDiscountPolicy(BaseDiscountPolicy discountPolicy);
    boolean removeDiscountPolicy(BaseDiscountPolicy discountPolicy);
    List<BaseDiscountPolicy> getAllDiscountPolicies();

    BaseDiscountPolicy getDiscountPolicyById(int policyId);
    void clear();
}