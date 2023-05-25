package BusinessLayer.Policies.PurchasePolicies;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BasePurchasePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "policy_id")
    protected int policyId;

    @Transient
    protected SystemLogger logger;

    public BasePurchasePolicy() {

    }

    public static List<String> getPurchasePolicyTypes() {
        List<String> ret = new ArrayList<>();
        ret.add("Category Time Restriction");
        ret.add("Product Max Quantity");
        ret.add("Product Min Quantity");
        ret.add("Product Time Restriction");
        return ret.stream().sorted().collect(Collectors.toList());
    }


    public BasePurchasePolicy(int policyId) {
        this.policyId = policyId;
        logger = new SystemLogger();
    }

    public abstract boolean evaluate(Map<Product, Integer> productList);

    public abstract BasePurchasePolicyDTO copyConstruct();

    public int getPolicyId() {
        return policyId;
    }
}
