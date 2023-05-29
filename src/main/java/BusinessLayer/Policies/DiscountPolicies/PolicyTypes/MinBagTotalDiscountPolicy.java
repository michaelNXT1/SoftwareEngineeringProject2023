package BusinessLayer.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes.MinBagTotalDiscountPolicyDTO;

import java.util.Map;

public class MinBagTotalDiscountPolicy extends BaseDiscountPolicy {
    private final double minTotal;

    public MinBagTotalDiscountPolicy(int policyId, double minTotal) throws Exception {
        super(policyId);
        if (minTotal <= 0) {
            logger.error("minimum total must be larger than 0 but is " + minTotal);
            throw new Exception("minimum total must be larger than 0");
        }
        this.minTotal = minTotal;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        return productList.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum() >= minTotal;
    }

    @Override
    public BaseDiscountPolicyDTO copyConstruct() throws Exception {
        return new MinBagTotalDiscountPolicyDTO(this);
    }

    public double getMinTotal() {
        return minTotal;
    }
}
