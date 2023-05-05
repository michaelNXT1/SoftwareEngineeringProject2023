package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Policies.PurchasePolicyExpression;

public abstract class BasePolicy implements PurchasePolicyExpression {
    protected int policyId;

    public int getPolicyId() {
        return policyId;
    }
}
