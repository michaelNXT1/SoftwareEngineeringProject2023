package Repositories;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;

import java.util.Map;

public interface IBaseDiscountPolicyMapRepository {
    void addDiscountPolicy(Discount discount, IBaseDiscountPolicyRepository discountPolicy);
    void removeDiscountPolicy(Discount discount);
    BaseDiscountPolicy getDiscountPolicy(Discount discount);
    Map<Discount, IBaseDiscountPolicyRepository> getAllDiscountPolicies();
}
