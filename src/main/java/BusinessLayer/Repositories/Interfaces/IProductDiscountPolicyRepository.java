package BusinessLayer.Repositories.Interfaces;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;

import java.util.List;
import java.util.Map;

public interface IProductDiscountPolicyRepository {
    void addProductDiscountPolicy(Discount discount, List<BaseDiscountPolicy> discountPolicies);
    void removeProductDiscountPolicy(Discount discount);
    List<BaseDiscountPolicy> getProductDiscountPolicies(Discount discount);
    Map<Discount, List<BaseDiscountPolicy>> getAllProductDiscountPolicies();
}
