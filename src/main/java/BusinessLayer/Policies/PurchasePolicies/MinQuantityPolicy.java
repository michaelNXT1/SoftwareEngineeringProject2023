package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Product;

import java.util.Map;

public class MinQuantityPolicy extends PurchasePolicy {
    private final int productId;
    private final int minQuantity;
    private final boolean allowNone;

    public MinQuantityPolicy(int policyId, int productId, int minQuantity, boolean allowNone) {
        super(policyId);
        this.productId = productId;
        this.minQuantity = minQuantity;
        this.allowNone = allowNone;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p.getProductId() == productId)
                return productList.get(p) >= minQuantity;
        return allowNone;
    }
}
