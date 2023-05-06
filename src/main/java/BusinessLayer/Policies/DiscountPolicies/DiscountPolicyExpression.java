package BusinessLayer.Policies.DiscountPolicies;

import BusinessLayer.Product;

import java.util.Map;

public interface DiscountPolicyExpression {

    enum JoinOperator{
        AND,
        OR,
        XOR
    }

    enum PolicyType {
        MIN_QUANTITY,
        MAX_QUANTITY,
        TIME_RESTRICTION
    }
    boolean evaluate(Map<Product, Integer> productList);
}
