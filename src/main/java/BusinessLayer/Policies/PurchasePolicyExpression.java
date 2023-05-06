package BusinessLayer.Policies;

import BusinessLayer.Product;

import java.util.Map;

public interface PurchasePolicyExpression {

    enum JoinOperator{
        OR,
        COND
    }

    enum PolicyType {
        MIN_QUANTITY,
        MAX_QUANTITY,
        TIME_RESTRICTION
    }
    boolean evaluate(Map<Product, Integer> productList);
}
