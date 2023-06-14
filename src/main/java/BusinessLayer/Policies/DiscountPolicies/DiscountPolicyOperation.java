package BusinessLayer.Policies.DiscountPolicies;

import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.DiscountPolicyOperationDTO;
import jakarta.persistence.*;
//import javax.persistence.*;
import java.util.Map;

@Entity
@DiscriminatorValue("CHILD")
public class DiscountPolicyOperation extends BaseDiscountPolicy {

    public enum JoinOperator {
        OR,
        XOR
    }

    @OneToOne
    private final BaseDiscountPolicy left;
    @Enumerated(EnumType.STRING)
    private final JoinOperator joinOperator;

    @OneToOne
    private final BaseDiscountPolicy right;

    public DiscountPolicyOperation(int policyId, BaseDiscountPolicy left, int joinOperator, BaseDiscountPolicy right,int store_id,int discount_id) {
        super(policyId,store_id, (long) discount_id);
        this.policyId = policyId;
        this.left = left;
        try {
            this.joinOperator = JoinOperator.values()[joinOperator];
        } catch (Exception e) {
            logger.error("illegal value for joinOperator");
            throw e;
        }
        this.right = right;
    }
    public DiscountPolicyOperation() {
        left = null;
        joinOperator = null;
        right = null;
    }

    public boolean evaluate(Map<Product, Integer> productList) {
        boolean leftValue = left.evaluate(productList);
        boolean rightValue = right.evaluate(productList);

        return switch (joinOperator) {
            case OR -> leftValue || rightValue;
            case XOR -> leftValue || rightValue && !(leftValue && rightValue);
        };
    }

    @Override
    public BaseDiscountPolicyDTO copyConstruct() throws Exception {
        return new DiscountPolicyOperationDTO(this);
    }

    public BaseDiscountPolicy getLeft() {
        return left;
    }

    public JoinOperator getJoinOperator() {
        return joinOperator;
    }

    public BaseDiscountPolicy getRight() {
        return right;
    }
}
