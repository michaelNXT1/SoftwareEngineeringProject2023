package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BasePurchasePolicy {
    protected SystemLogger logger;

    public static List<String> getPurchasePolicyTypes() {
        List<String> ret = new ArrayList<>();
        ret.add("Category Time Restriction");
        ret.add("Product Max Quantity");
        ret.add("Product Min Quantity");
        ret.add("Product Time Restriction");
        return ret.stream().sorted().collect(Collectors.toList());
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
