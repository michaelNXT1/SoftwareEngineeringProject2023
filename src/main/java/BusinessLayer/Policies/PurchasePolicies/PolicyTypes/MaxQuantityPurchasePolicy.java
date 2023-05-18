package BusinessLayer.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.PolicyTypes.MaxQuantityPurchasePolicyDTO;

import java.util.Map;

public class MaxQuantityPurchasePolicy extends BasePurchasePolicy {
    private final Product product;
    private final int maxQuantity;

    public MaxQuantityPurchasePolicy(int policyId, Product product, int maxQuantity) throws Exception {
        super(policyId);
        if (maxQuantity <= 0) {
            logger.error("Max quantity must be larger than 0 but is" + maxQuantity);
            throw new Exception("Max quantity must be larger than 0");
        }
        this.product = product;
        this.maxQuantity = maxQuantity;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p == product)
                return productList.get(p) <= maxQuantity;
        return true;
    }

    @Override
    public BasePurchasePolicyDTO copyConstruct() {
        return new MaxQuantityPurchasePolicyDTO(this);
    }

    public Product getProduct() {
        return product;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }
}
