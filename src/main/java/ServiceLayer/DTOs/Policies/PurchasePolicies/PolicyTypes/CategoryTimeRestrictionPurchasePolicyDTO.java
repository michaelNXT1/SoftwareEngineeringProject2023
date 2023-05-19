package ServiceLayer.DTOs.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.CategoryTimeRestrictionPurchasePolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public class CategoryTimeRestrictionPurchasePolicyDTO extends BasePurchasePolicyDTO {
    private final String category;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public CategoryTimeRestrictionPurchasePolicyDTO(CategoryTimeRestrictionPurchasePolicy pp) {
        super(pp);
        this.category = pp.getCategory();
        this.startTime = pp.getStartTime();
        this.endTime = pp.getEndTime();
    }

    @Override
    public String toString() {
        return "Category " + category + " allowed between " + startTime + " and " + endTime;
    }
}
