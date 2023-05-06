package BusinessLayer.Policies.DiscountPolicies;

import BusinessLayer.Product;

import java.util.Map;

public class MinBagTotalDiscountPolicy extends BaseDiscountPolicy {
    private final int minTotal;

    public MinBagTotalDiscountPolicy(int policyId, int minTotal) throws Exception {
        super(policyId);;
        if (minTotal <= 0)
            throw new Exception("minimum total must be larger than 0");
        this.minTotal = minTotal;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        return productList.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum() >= minTotal;
    }
}
