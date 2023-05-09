package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Product;

import java.util.Map;

public abstract class BasePolicy {

    public enum JoinOperator{
        OR,
        COND
    }
    protected int policyId;

    public abstract boolean evaluate(Map<Product, Integer> productList);
    public int getPolicyId() {
        return policyId;
    }
}
