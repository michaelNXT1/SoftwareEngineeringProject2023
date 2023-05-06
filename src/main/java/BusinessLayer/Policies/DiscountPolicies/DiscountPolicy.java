package BusinessLayer.Policies.DiscountPolicies;

import BusinessLayer.Product;

import java.util.Map;

public class DiscountPolicy {

    public boolean checkPolicyFulfilled(Map<Product, Integer> productList){
        return true;
    }
}
