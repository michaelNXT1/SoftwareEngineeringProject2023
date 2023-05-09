package BusinessLayer.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.PurchasePolicy;
import BusinessLayer.Product;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public class CategoryTimeRestrictionPolicy extends PurchasePolicy {
    private final String category;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public CategoryTimeRestrictionPolicy(int policyId, String category, LocalTime startTime, LocalTime endTime) throws Exception {
        super(policyId);
        if (startTime.equals(endTime))
            throw new Exception("Start time cannot be the same as end time");
        if (Objects.equals(category, "") || category == null)
            throw new Exception("category cannot be empty");
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
}
