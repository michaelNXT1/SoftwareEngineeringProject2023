package BusinessLayer.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.PolicyTypes.ProductTimeRestrictionPurchasePolicyDTO;

import java.time.LocalTime;
import java.util.Map;

public class ProductTimeRestrictionPurchasePolicy extends BasePurchasePolicy {
    private final Product product;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public ProductTimeRestrictionPurchasePolicy(int policyId, Product product, LocalTime startTime, LocalTime endTime) throws Exception {
        super(policyId);
        if (startTime.equals(endTime)) {
            logger.error("Start time cannot be the same as end time");
            throw new Exception("Start time cannot be the same as end time");
        }
        this.product = product;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p == product)
                return LocalTime.now().isBefore(endTime) && LocalTime.now().isAfter(startTime);
        return true;
    }

    @Override
    public BasePurchasePolicyDTO copyConstruct() {
        return new ProductTimeRestrictionPurchasePolicyDTO(this);
    }

    public Product getProduct() {
        return product;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
