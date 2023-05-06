package BusinessLayer.Policies.PurchasePolicies;

public abstract class BasePolicy implements PurchasePolicyExpression {
    protected int policyId;

    public int getPolicyId() {
        return policyId;
    }
}
