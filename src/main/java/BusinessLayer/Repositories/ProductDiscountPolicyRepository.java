package BusinessLayer.Repositories;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Repositories.Interfaces.IProductDiscountPolicyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDiscountPolicyRepository implements IProductDiscountPolicyRepository {
    private final Map<Discount, List<BaseDiscountPolicy>> productDiscountPolicyMap;

    public ProductDiscountPolicyRepository() {
        this.productDiscountPolicyMap = new HashMap<>();
    }

    @Override
    public void addProductDiscountPolicy(Discount discount, List<BaseDiscountPolicy> discountPolicies) {
        productDiscountPolicyMap.put(discount, discountPolicies);
    }

    @Override
    public void removeProductDiscountPolicy(Discount discount) {
        productDiscountPolicyMap.remove(discount);
    }

    @Override
    public List<BaseDiscountPolicy> getProductDiscountPolicies(Discount discount) {
        return productDiscountPolicyMap.get(discount);
    }

    @Override
    public Map<Discount, List<BaseDiscountPolicy>> getAllProductDiscountPolicies() {
        return productDiscountPolicyMap;
    }
}
