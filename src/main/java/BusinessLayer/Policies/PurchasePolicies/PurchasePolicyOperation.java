package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.PurchasePolicyOperationDTO;

import java.util.Map;

public class PurchasePolicyOperation extends BasePurchasePolicy {
    private BasePurchasePolicy left;
    private BasePurchasePolicy.JoinOperator joinOperator;
    private BasePurchasePolicy right;

    public PurchasePolicyOperation(int policyId, BasePurchasePolicy left, int joinOperator, BasePurchasePolicy right) {
        super(policyId);
        this.left = left;
        try {
            this.joinOperator = JoinOperator.values()[joinOperator];
        } catch (Exception e) {
            logger.error("illegal value for joinOperator");
            throw e;
        }
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

    @Override
    public BasePurchasePolicyDTO copyConstruct() {
        return new PurchasePolicyOperationDTO(this);
    }

    public BasePurchasePolicy getLeft() {
        return left;
    }

    public JoinOperator getJoinOperator() {
        return joinOperator;
    }

    public BasePurchasePolicy getRight() {
        return right;
    }
}
