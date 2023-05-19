package ServiceLayer.DTOs.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.MaxQuantityPurchasePolicy;
import BusinessLayer.Policies.PurchasePolicies.PolicyTypes.MinQuantityPurchasePolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.ProductDTO;

import java.util.Map;

public class MinQuantityPurchasePolicyDTO extends BasePurchasePolicyDTO {
    private final ProductDTO product;
    private final int minQuantity;
    private final boolean allowNone;

    public MinQuantityPurchasePolicyDTO(MinQuantityPurchasePolicy mqpp) {
        super(mqpp);
        this.product = new ProductDTO(mqpp.getProduct());
        this.minQuantity = mqpp.getMinQuantity();
        this.allowNone = mqpp.isAllowNone();
    }

    @Override
    public String toString() {
        return "Max quantity of " + product.getProductName() + " is " + minQuantity;
    }
}
