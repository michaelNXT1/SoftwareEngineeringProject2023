package BusinessLayer.Policies.DiscountPolicies;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import jakarta.persistence.*;
//import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "base_discount_policy")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "BaseDiscountPolicy", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseDiscountPolicy {
    @Id
    @Column(name = "policy_id")
    protected int policyId;

    @Transient
    protected final SystemLogger logger;

    public BaseDiscountPolicy() {
        logger = new SystemLogger();
    }

    public static List<String> getDiscountPolicyTypes() {
        List<String> ret = new ArrayList<>();
        ret.add("Product Max Quantity");
        ret.add("Product Min Quantity");
        ret.add("Min Bag Total");
        return ret.stream().sorted().collect(Collectors.toList());
    }

    public BaseDiscountPolicy(int policyId) {
        this.logger = new SystemLogger();
        this.policyId = policyId;
    }

    public abstract boolean evaluate(Map<Product, Integer> productList);

    public int getPolicyId() {
        return policyId;
    }

    public abstract BaseDiscountPolicyDTO copyConstruct() throws Exception;
}
