package BusinessLayer.Policies;

import BusinessLayer.Policies.PurchasePolicies.BasePolicy;
import BusinessLayer.Product;

import java.util.Map;

public class Operation extends BasePolicy {
    private PurchasePolicyExpression left;
    private PurchasePolicyExpression.JoinOperator joinOperator;
    private PurchasePolicyExpression right;

    public Operation(int policyId, PurchasePolicyExpression left,JoinOperator joinOperator,  PurchasePolicyExpression right) {
        this.policyId = policyId;
        this.left = left;
        this.joinOperator = joinOperator;
        this.right = right;
    }

    public boolean evaluate(Map<Product, Integer> productList) {
        boolean leftValue = left.evaluate(productList);
        boolean rightValue = right.evaluate(productList);

        return switch (joinOperator) {
            case OR -> leftValue || rightValue;
            case COND -> !leftValue || rightValue;
            default -> throw new IllegalArgumentException("Invalid operator: " + joinOperator);
        };
    }
}
