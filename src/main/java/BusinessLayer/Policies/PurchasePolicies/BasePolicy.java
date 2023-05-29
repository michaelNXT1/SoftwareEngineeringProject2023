package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Product;

import java.util.Map;

public abstract class BasePolicy {
    protected SystemLogger logger;

    public enum JoinOperator{
        OR,
        COND
    }
    protected int policyId;
    public BasePolicy(){
        logger=new SystemLogger();
    }

    public abstract boolean evaluate(Map<Product, Integer> productList);
    public int getPolicyId() {
        return policyId;
    }
}
