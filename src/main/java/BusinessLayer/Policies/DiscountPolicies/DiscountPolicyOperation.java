package BusinessLayer.Policies.DiscountPolicies;

import BusinessLayer.Product;

import java.util.Map;

public class DiscountPolicyOperation extends BaseDiscountPolicy {
    private final BaseDiscountPolicy left;
    private final JoinOperator joinOperator;
    private final BaseDiscountPolicy right;

    public DiscountPolicyOperation(int policyId, BaseDiscountPolicy left, int joinOperator, BaseDiscountPolicy right) {
        super(policyId);
        this.policyId = policyId;
        this.left = left;
        this.joinOperator = JoinOperator.values()[joinOperator];
        this.right = right;
    }

    public boolean evaluate(Map<Product, Integer> productList) {
        boolean leftValue = left.evaluate(productList);
        boolean rightValue = right.evaluate(productList);

        return switch (joinOperator) {
            case AND -> leftValue && rightValue;
            case OR -> leftValue || rightValue;
//            case XOR-> //TODO figure it out.
            default -> throw new IllegalArgumentException("Invalid operator: " + joinOperator);
        };
    }
}
