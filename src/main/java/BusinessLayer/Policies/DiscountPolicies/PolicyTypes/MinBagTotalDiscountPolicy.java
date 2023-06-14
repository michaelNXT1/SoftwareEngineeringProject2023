package BusinessLayer.Policies.DiscountPolicies.PolicyTypes;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.PolicyTypes.MinBagTotalDiscountPolicyDTO;
//import javax.persistence.*;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "min_bag_total_discount_policy")
@DiscriminatorValue("CHILD")
public class MinBagTotalDiscountPolicy extends BaseDiscountPolicy {
    @Column(name = "min_total")
    private final double minTotal;

    public MinBagTotalDiscountPolicy(int policyId, double minTotal,int store_id,int discount_id) throws Exception {
        super(policyId,store_id,discount_id);
        if (minTotal <= 0) {
            logger.error("minimum total must be larger than 0 but is " + minTotal);
            throw new Exception("minimum total must be larger than 0");
        }
        this.minTotal = minTotal;
    }

    public MinBagTotalDiscountPolicy() {
        this.minTotal = 0;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        return productList.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum() >= minTotal;
    }

    @Override
    public BaseDiscountPolicyDTO copyConstruct() throws Exception {
        return new MinBagTotalDiscountPolicyDTO(this);
    }

    public double getMinTotal() {
        return minTotal;
    }
}
