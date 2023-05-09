package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Product;

import java.util.Map;

public class Operation extends BasePolicy {
    private BasePolicy left;
    private BasePolicy.JoinOperator joinOperator;
    private BasePolicy right;

    public Operation(int policyId, BasePolicy left,JoinOperator joinOperator,  BasePolicy right) {
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
