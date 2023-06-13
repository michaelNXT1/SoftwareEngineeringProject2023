package BusinessLayer.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes.MinQuantityDiscountPolicyDTO;
import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "min_quantity_discount_policy")
@DiscriminatorValue("CHILD")
public class MinQuantityDiscountPolicy extends BaseDiscountPolicy {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private final Product product;

    @Column(name = "min_quantity")
    private final int minQuantity;

    @Column(name = "allow_none")
    private final boolean allowNone;

    public MinQuantityDiscountPolicy(int policyId, Product product, int minQuantity, boolean allowNone,int store_id,int discount_id) throws Exception {
        super(policyId,store_id,discount_id);
        if (minQuantity <= 0) {
            logger.error("Min quantity must be larger than 0 but is " + minQuantity);
            throw new Exception("Min quantity must be larger than 0");
        }
        this.product = product;
        this.minQuantity = minQuantity;
        this.allowNone = allowNone;
    }

    public MinQuantityDiscountPolicy() {
        this.product = null;
        this.minQuantity = 0;
        this.allowNone = false;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p == product)
                return productList.get(p) >= minQuantity;
        return allowNone;
    }

    @Override
    public BaseDiscountPolicyDTO copyConstruct() throws Exception {
        return new MinQuantityDiscountPolicyDTO(this);
    }

    public Product getProduct() {
        return product;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public boolean isAllowNone() {
        return allowNone;
    }
}
