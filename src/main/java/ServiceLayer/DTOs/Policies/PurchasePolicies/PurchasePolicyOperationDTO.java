package ServiceLayer.DTOs.Policies.PurchasePolicies;

import BusinessLayer.Policies.PurchasePolicies.PurchasePolicyOperation;

public class PurchasePolicyOperationDTO extends BasePurchasePolicyDTO {

    public enum JoinOperator {
        OR,
        COND
    }

    private final BasePurchasePolicyDTO left;
    private final JoinOperator joinOperator;
    private final BasePurchasePolicyDTO right;

    public PurchasePolicyOperationDTO(PurchasePolicyOperation ppo) {
        super(ppo);
        this.left = ppo.getLeft().copyConstruct();
        switch (ppo.getJoinOperator()) {
            case OR -> this.joinOperator = JoinOperator.OR;
            default -> this.joinOperator = JoinOperator.COND;
        }
        this.right = ppo.getRight().copyConstruct();
    }

    @Override
    public String toString() {
        return switch (joinOperator) {
            case OR -> "(" + left.toString() + ")\nOR\n(" + right.toString() + ")";
            case COND -> "If\n(" + left.toString() + ")\nis fulfilled, then check\n(" + right.toString() + ")";
        };
    }
}
