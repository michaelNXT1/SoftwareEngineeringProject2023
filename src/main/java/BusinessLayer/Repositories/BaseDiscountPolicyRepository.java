package BusinessLayer.Repositories;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Repositories.Interfaces.IBaseDiscountPolicyRepository;

import java.util.ArrayList;
import java.util.List;

public class BaseDiscountPolicyRepository implements IBaseDiscountPolicyRepository {
    private final List<BaseDiscountPolicy> discountPolicies;

    public BaseDiscountPolicyRepository() {
        this.discountPolicies = new ArrayList<>();
    }

    @Override
    public void addDiscountPolicy(BaseDiscountPolicy discountPolicy) {
        discountPolicies.add(discountPolicy);
    }

    @Override
    public void removeDiscountPolicy(BaseDiscountPolicy discountPolicy) {
        discountPolicies.remove(discountPolicy);
    }

    @Override
    public List<BaseDiscountPolicy> getAllDiscountPolicies() {
        return discountPolicies;
    }
}
