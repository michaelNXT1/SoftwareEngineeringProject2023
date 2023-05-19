package BusinessLayer.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.PolicyTypes.MinQuantityPurchasePolicyDTO;

import java.util.Map;

public class MinQuantityPurchasePolicy extends BasePurchasePolicy {
    private final Product product;
    private final int minQuantity;
    private final boolean allowNone;

    public MinQuantityPurchasePolicy(int policyId, Product product, int minQuantity, boolean allowNone) throws Exception {
        super(policyId);
        if (minQuantity <= 0) {
            logger.error("Min quantity must be larger than 0 but is " + minQuantity);
            throw new Exception("Min quantity must be larger than 0");
        }
        this.product = product;
        this.minQuantity = minQuantity;
        this.allowNone = allowNone;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p == product)
                return productList.get(p) >= minQuantity;
        return allowNone;
    }

    @Override
    public BasePurchasePolicyDTO copyConstruct() {
        return new MinQuantityPurchasePolicyDTO(this);
    }

    public Product getProduct() {
        return product;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public boolean isAllowNone() {
        return allowNone;
    }
}
