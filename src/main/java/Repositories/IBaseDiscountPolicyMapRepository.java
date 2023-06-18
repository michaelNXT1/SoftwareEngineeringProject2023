package Repositories;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;

import java.util.List;
import java.util.Map;

public interface IBaseDiscountPolicyMapRepository {
    int addDiscountPolicy(BaseDiscountPolicy discount);
    void removeDiscountPolicy(BaseDiscountPolicy discount);
    BaseDiscountPolicy getDiscountPolicy(Discount discount);
    BaseDiscountPolicy getDiscountPolicyById(Integer id);
    List<BaseDiscountPolicy> getAllDiscountPolicies();

    void clear();

    void updateBaseDiscountPolicy(BaseDiscountPolicy baseDiscountPolicy);
}
