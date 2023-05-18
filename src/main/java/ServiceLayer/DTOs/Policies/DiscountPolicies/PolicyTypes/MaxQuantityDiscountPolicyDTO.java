package ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MaxQuantityDiscountPolicy;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.ProductDTO;

public class MaxQuantityDiscountPolicyDTO extends BaseDiscountPolicyDTO {
    private final ProductDTO product;
    private final int minQuantity;
    private final boolean allowNone;

    public MaxQuantityDiscountPolicyDTO(MaxQuantityDiscountPolicy maxQuantityDiscountPolicy) throws Exception {
        super(maxQuantityDiscountPolicy);
        this.product = new ProductDTO(maxQuantityDiscountPolicy.getProduct());
        this.minQuantity = maxQuantityDiscountPolicy.getMinQuantity();
        this.allowNone = maxQuantityDiscountPolicy.isAllowNone();
    }

    public ProductDTO getProduct() {
        return product;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public boolean isAllowNone() {
        return allowNone;
    }

    @Override
    public String toString() {
        return "Max quantity of " + product.getProductName() + " is " + minQuantity;
    }
}
