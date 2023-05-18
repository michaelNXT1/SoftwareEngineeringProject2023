package ServiceLayer.DTOs.Policies.DiscountPolicies;

import BusinessLayer.Policies.DiscountPolicies.DiscountPolicyOperation;

public class DiscountPolicyOperationDTO extends BaseDiscountPolicyDTO {

    public enum JoinOperator {
        OR,
        XOR
    }

    private final BaseDiscountPolicyDTO left;
    private final JoinOperator joinOperator;
    private final BaseDiscountPolicyDTO right;

    public DiscountPolicyOperationDTO(DiscountPolicyOperation dpo) throws Exception {
        super(dpo);
        this.left = dpo.getLeft().copyConstruct();
        switch (dpo.getJoinOperator()) {
            case OR -> this.joinOperator = JoinOperator.OR;
            default -> this.joinOperator = JoinOperator.XOR;
        }
        this.right = dpo.getRight().copyConstruct();
    }
}
