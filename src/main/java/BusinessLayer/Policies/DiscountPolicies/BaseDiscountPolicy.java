package BusinessLayer.Policies.DiscountPolicies;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Product;

import java.util.Map;

public abstract class BaseDiscountPolicy {
    protected int policyId;
    protected final SystemLogger logger;

    public enum JoinOperator {
        AND,
        OR,
        XOR
    }

    public BaseDiscountPolicy(int policyId) {
        this.logger=new SystemLogger();
        this.policyId = policyId;
    }

    public abstract boolean evaluate(Map<Product, Integer> productList);

    public int getPolicyId() {
        return policyId;
    }
}
