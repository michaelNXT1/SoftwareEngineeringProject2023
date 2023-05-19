package ServiceLayer.DTOs.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MaxQuantityDiscountPolicy;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.MaxQuantityPurchasePolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.ProductDTO;

import java.util.Map;

public class MaxQuantityPurchasePolicyDTO extends BasePurchasePolicyDTO {
    private final ProductDTO product;
    private final int maxQuantity;

    public MaxQuantityPurchasePolicyDTO(MaxQuantityPurchasePolicy mqpp) {
        super(mqpp);
        this.product = new ProductDTO(mqpp.getProduct());
        this.maxQuantity = mqpp.getMaxQuantity();
    }

    @Override
    public String toString() {
        return "Max quantity of " + product.getProductName() + " is " + maxQuantity;
    }
}
