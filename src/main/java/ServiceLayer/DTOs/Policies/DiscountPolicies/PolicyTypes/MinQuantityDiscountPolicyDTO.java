package ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MinQuantityDiscountPolicy;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.ProductDTO;

public class MinQuantityDiscountPolicyDTO extends BaseDiscountPolicyDTO {
    private final ProductDTO product;
    private final int minQuantity;
    private final boolean allowNone;

    public MinQuantityDiscountPolicyDTO(MinQuantityDiscountPolicy minQuantityDiscountPolicy) {
        super(minQuantityDiscountPolicy);
        this.product = new ProductDTO(minQuantityDiscountPolicy.getProduct());
        this.minQuantity = minQuantityDiscountPolicy.getMinQuantity();
        this.allowNone = minQuantityDiscountPolicy.isAllowNone();
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
        return "Min quantity of " + product.getProductName() + " is " + minQuantity;
    }
}
