package ServiceLayer.DTOs.Policies.PurchasePolicies;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import BusinessLayer.Product;

import java.util.Map;

public abstract class BasePurchasePolicyDTO {
    protected int policyId;


    public BasePurchasePolicyDTO(BasePurchasePolicy bpp) {
        this.policyId = bpp.getPolicyId();
    }

    public int getPolicyId() {
        return policyId;
    }
}
