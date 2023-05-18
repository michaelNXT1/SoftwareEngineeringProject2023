package ServiceLayer.DTOs.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.ProductTimeRestrictionPurchasePolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.ProductDTO;

import java.time.LocalTime;

public class ProductTimeRestrictionPurchasePolicyDTO extends BasePurchasePolicyDTO {
    private final ProductDTO product;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public ProductTimeRestrictionPurchasePolicyDTO(ProductTimeRestrictionPurchasePolicy ptrpp) {
        super(ptrpp);
        this.product = new ProductDTO(ptrpp.getProduct());
        this.startTime = ptrpp.getStartTime();
        this.endTime = ptrpp.getEndTime();
    }

    @Override
    public String toString() {
        return "Product " + product.getProductName() + " allowed between " + startTime + " and " + endTime;
    }
}
