package ServiceLayer.DTOs.Policies.PurchasePolicies;

import BusinessLayer.Policies.PurchasePolicies.PurchasePolicyOperation;
import BusinessLayer.Product;

import java.util.Map;

public class PurchasePolicyOperationDTO extends BasePurchasePolicyDTO {

    public enum JoinOperator {
        OR,
        COND
    }

    private BasePurchasePolicyDTO left;
    private JoinOperator joinOperator;
    private BasePurchasePolicyDTO right;

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
        if (joinOperator == JoinOperator.OR)
            return "(" + left.toString() + ")\nOR\n(" + right.toString() + ")";
        else
            return "If\n(" + left.toString() + "}\nis fulfilled, then\n(" + right.toString() + ")";
    }
}
