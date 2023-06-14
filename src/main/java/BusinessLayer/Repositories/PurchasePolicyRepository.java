package BusinessLayer.Repositories;

import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import BusinessLayer.Repositories.Interfaces.IPurchasePolicyRepository;

import java.util.ArrayList;
import java.util.List;

public class PurchasePolicyRepository implements IPurchasePolicyRepository {
    private final List<BasePurchasePolicy> purchasePolicies;

    public PurchasePolicyRepository() {
        this.purchasePolicies = new ArrayList<>();
    }

    @Override
    public void addPurchasePolicy(BasePurchasePolicy purchasePolicy) {
        purchasePolicies.add(purchasePolicy);
    }

    @Override
    public void removePurchasePolicy(BasePurchasePolicy purchasePolicy) {
        purchasePolicies.remove(purchasePolicy);
    }

    @Override
    public List<BasePurchasePolicy> getAllPurchasePolicies() {
        return purchasePolicies;
    }

}
