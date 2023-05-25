package BusinessLayer.Policies.PurchasePolicies.PolicyTypes;

import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.PolicyTypes.ProductTimeRestrictionPurchasePolicyDTO;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalTime;
import java.util.Map;
@Entity
public class ProductTimeRestrictionPurchasePolicy extends BasePurchasePolicy {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private final Product product;
    @Column(name = "start_time")
    private final LocalTime startTime;
    @Column(name = "end_time")
    private final LocalTime endTime;

    public ProductTimeRestrictionPurchasePolicy(int policyId, Product product, LocalTime startTime, LocalTime endTime) throws Exception {
        super(policyId);
        if (startTime.equals(endTime)) {
            logger.error("Start time cannot be the same as end time");
            throw new Exception("Start time cannot be the same as end time");
        }
        this.product = product;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ProductTimeRestrictionPurchasePolicy() {
        this.product = null;
        this.startTime = null;
        this.endTime = null;
    }

    @Override
    public boolean evaluate(Map<Product, Integer> productList) {
        for (Product p : productList.keySet())
            if (p == product)
                return LocalTime.now().isBefore(endTime) && LocalTime.now().isAfter(startTime);
        return true;
    }

    @Override
    public BasePurchasePolicyDTO copyConstruct() {
        return new ProductTimeRestrictionPurchasePolicyDTO(this);
    }

    public Product getProduct() {
        return product;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
