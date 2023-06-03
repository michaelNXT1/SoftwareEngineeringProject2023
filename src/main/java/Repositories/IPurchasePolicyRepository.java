package Repositories;

import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;

import java.util.List;

public interface IPurchasePolicyRepository {
    void addPurchasePolicy(BasePurchasePolicy purchasePolicy);
    void removePurchasePolicy(BasePurchasePolicy purchasePolicy);
    List<BasePurchasePolicy> getAllPurchasePolicies();
}
