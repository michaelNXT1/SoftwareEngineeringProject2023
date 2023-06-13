package BusinessLayer.Policies.DiscountPolicies;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Product;
import BusinessLayer.Store;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
//import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "base_discount_policy")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "BaseDiscountPolicy", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseDiscountPolicy {
    @Id
    @Column(name = "policy_id")
    protected int policyId;

    @Column(name = "store_id")
    protected Integer store_id;

    @Column(name = "discount_id")
    protected Integer discount_id;

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

    public BaseDiscountPolicy(int policyId, int store_id,int discounts) {
        this.logger = new SystemLogger();
        this.policyId = policyId;
        this.store_id = store_id;
        this.discount_id = discounts;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public Integer getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(Integer discount_id) {
        this.discount_id = discount_id;
    }

    public Integer getStore() {
        return store_id;
    }

    public void setStore(Integer store) {
        this.store_id = store;
    }
    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public SystemLogger getLogger() {
        return logger;
    }

    public abstract boolean evaluate(Map<Product, Integer> productList);

    public int getPolicyId() {
        return policyId;
    }

    public abstract BaseDiscountPolicyDTO copyConstruct() throws Exception;
}
