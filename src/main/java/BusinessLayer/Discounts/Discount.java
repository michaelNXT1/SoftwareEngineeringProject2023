package BusinessLayer.Discounts;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
//import javax.persistence.*;
import jakarta.persistence.*;


@Entity
@Table(name = "discounts")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "Discount", discriminatorType = DiscriminatorType.STRING)
abstract public class Discount {

    public enum CompositionType {
        ADDITION,
        MAX
    }
    @Id
    @Column(name = "discount_id")
    protected final int discountId;

    @Column(name = "discount_percentage")
    protected final double discountPercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "composition_type")
    protected final CompositionType compositionType;

    @Transient
    protected final SystemLogger logger;

    public Discount(int discountId, double discountPercentage, int compositionType) throws Exception {
        logger = new SystemLogger();
        this.discountId = discountId;
        if (discountPercentage <= 0.0 || 1.0 < discountPercentage) {
            logger.error("discount percentage illegal: " + discountPercentage);
            throw new Exception("discount percentage illegal");
        }
        this.discountPercentage = discountPercentage;
        this.compositionType = CompositionType.values()[compositionType];
    }
    public Discount() {
        logger = new SystemLogger();
        this.discountId = 0;
        this.discountPercentage = 0;
        this.compositionType = null;
    }

    public int getDiscountId() {
        return discountId;
    }

    public abstract boolean checkApplies(Product p);

    public double calculateNewPercentage(double currentPercentage) {
        return switch (compositionType) {
            case ADDITION -> Math.max(currentPercentage + discountPercentage, 1.0);
            case MAX -> Math.max(currentPercentage, discountPercentage);
        };
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public CompositionType getCompositionType() {
        return compositionType;
    }

    public abstract DiscountDTO copyConstruct();
}
