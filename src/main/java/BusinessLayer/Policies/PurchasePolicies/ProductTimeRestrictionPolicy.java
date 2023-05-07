package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Product;

import java.time.LocalTime;
import java.util.Map;

public class ProductTimeRestrictionPolicy extends PurchasePolicy {
    private final int productId;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public ProductTimeRestrictionPolicy(int policyId, int productId, LocalTime startTime, LocalTime endTime) throws Exception {
        super(policyId);
        if(startTime.equals(endTime))
            throw new Exception("Start time cannot be the same as end time");
        this.productId = productId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p.getProductId() == productId)
                return LocalTime.now().isBefore(endTime) && LocalTime.now().isAfter(startTime);
        return true;
    }
}
