package BusinessLayer.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes.MaxQuantityDiscountPolicyDTO;
import jakarta.persistence.*;
//import javax.persistence.*;

import java.util.Map;

@Entity
@Table(name = "max_quantity_discount_policy")
@DiscriminatorValue("CHILD")
public class MaxQuantityDiscountPolicy extends BaseDiscountPolicy {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "min_quantity")
    private int minQuantity;

    public MaxQuantityDiscountPolicy(int policyId, Product product, int maxQuantity) throws Exception {
        super(policyId);
        if (maxQuantity <= 0) {
            logger.error("Max quantity must be larger than 0 but is " + maxQuantity);
            throw new Exception("Max quantity must be larger than 0");
        }
        this.product = product;
        this.minQuantity = maxQuantity;
    }

    public MaxQuantityDiscountPolicy() {
        super();
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p == product)
                return productList.get(p) <= minQuantity;
        return true;
    }

    @Override
    public BaseDiscountPolicyDTO copyConstruct() throws Exception {
        return new MaxQuantityDiscountPolicyDTO(this);
    }

    public Product getProduct() {
        return product;
    }

    public int getMinQuantity() {
        return minQuantity;
    }
}
