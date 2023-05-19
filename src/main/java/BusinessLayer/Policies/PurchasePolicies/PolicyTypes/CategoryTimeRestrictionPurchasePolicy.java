package BusinessLayer.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.PolicyTypes.CategoryTimeRestrictionPurchasePolicyDTO;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public class CategoryTimeRestrictionPurchasePolicy extends BasePurchasePolicy {
    private final String category;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public CategoryTimeRestrictionPurchasePolicy(int policyId, String category, LocalTime startTime, LocalTime endTime) throws Exception {
        super(policyId);
        if (startTime.equals(endTime)) {
            logger.error("Start time cannot be the same as end time");
            throw new Exception("Start time cannot be the same as end time");
        }
        if (Objects.equals(category, "") || category == null) {
            logger.error("category cannot be empty");
            throw new Exception("category cannot be empty");
        }
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (Objects.equals(p.getCategory(), category))
                return LocalTime.now().isBefore(endTime) && LocalTime.now().isAfter(startTime);
        return true;
    }

    @Override
    public BasePurchasePolicyDTO copyConstruct() {
        return new CategoryTimeRestrictionPurchasePolicyDTO(this);
    }

    public String getCategory() {
        return category;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
