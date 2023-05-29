package ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MaxQuantityDiscountPolicy;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.ProductDTO;

public class MaxQuantityDiscountPolicyDTO extends BaseDiscountPolicyDTO {
    private final ProductDTO product;
    private final int minQuantity;

    public MaxQuantityDiscountPolicyDTO(MaxQuantityDiscountPolicy maxQuantityDiscountPolicy) {
        super(maxQuantityDiscountPolicy);
        this.product = new ProductDTO(maxQuantityDiscountPolicy.getProduct());
        this.minQuantity = maxQuantityDiscountPolicy.getMinQuantity();
    }

    public ProductDTO getProduct() {
        return product;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    @Override
    public String toString() {
        return "Max quantity of " + product.getProductName() + " is " + minQuantity;
    }
}
