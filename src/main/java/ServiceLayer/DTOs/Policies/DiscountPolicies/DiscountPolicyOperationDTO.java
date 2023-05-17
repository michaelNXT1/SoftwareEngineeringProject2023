package ServiceLayer.DTOs.Policies.DiscountPolicies;

import BusinessLayer.Policies.DiscountPolicies.DiscountPolicyOperation;

public class DiscountPolicyOperationDTO extends BaseDiscountPolicyDTO {

    public enum JoinOperator {
        AND,
        OR,
        XOR
    }

    private final BaseDiscountPolicyDTO left;
    private final DiscountPolicyOperation.JoinOperator joinOperator;
    private final BaseDiscountPolicyDTO right;

    public DiscountPolicyOperationDTO(DiscountPolicyOperation dpo) throws Exception {
        super(dpo);
        this.left = dpo.getLeft().copyConstruct();
        switch (dpo.getJoinOperator()) {
            case AND -> this.joinOperator = DiscountPolicyOperation.JoinOperator.AND;
            case OR -> this.joinOperator = DiscountPolicyOperation.JoinOperator.OR;
            default -> this.joinOperator = DiscountPolicyOperation.JoinOperator.XOR;
        }
        this.right = dpo.getRight().copyConstruct();
    }
}
