package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.PurchasePolicyOperationDTO;
//import javax.persistence.*;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "ProductTimeRestrictionPurchasePolicy")
@DiscriminatorValue("CHILD")
public class PurchasePolicyOperation extends BasePurchasePolicy {

    public enum JoinOperator {
        OR,
        COND
    }

    @OneToOne
    private final BasePurchasePolicy left;

    @Enumerated(EnumType.STRING)
    private final JoinOperator joinOperator;
    @OneToOne
    private final BasePurchasePolicy right;

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
    public PurchasePolicyOperation() {

        left = null;
        joinOperator = null;
        right = null;
    }

    public boolean evaluate(Map<Product, Integer> productList) {
        boolean leftValue = left.evaluate(productList);
        boolean rightValue = right.evaluate(productList);

        return switch (joinOperator) {
            case OR -> leftValue || rightValue;
            case COND -> !leftValue || rightValue;
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
