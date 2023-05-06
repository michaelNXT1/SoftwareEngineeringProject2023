package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Product;

import java.util.Map;

public abstract class PurchasePolicy extends BasePolicy {

    protected PurchasePolicy(int policyId) {
        this.policyId = policyId;
    }

    public abstract boolean evaluate(Map<Product, Integer> productList);
}
