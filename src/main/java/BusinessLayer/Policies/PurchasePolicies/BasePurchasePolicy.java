package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;

import java.util.Map;

public abstract class BasePurchasePolicy {
    protected SystemLogger logger;

    public enum JoinOperator {
        OR,
        COND
    }

    protected int policyId;

    public BasePurchasePolicy(int policyId) {
        this.policyId = policyId;
        logger = new SystemLogger();
    }

    public abstract boolean evaluate(Map<Product, Integer> productList);

    public abstract BasePurchasePolicyDTO copyConstruct();

    public int getPolicyId() {
        return policyId;
    }
}
