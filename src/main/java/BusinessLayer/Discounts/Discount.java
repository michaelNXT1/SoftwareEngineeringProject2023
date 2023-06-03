package BusinessLayer.Discounts;

import BusinessLayer.Logger.SystemLogger;
import BusinessLayer.Product;
import ServiceLayer.DTOs.Discounts.CategoryDiscountDTO;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Discounts.ProductDiscountDTO;
import ServiceLayer.DTOs.Discounts.StoreDiscountDTO;

import java.util.logging.Logger;

abstract public class Discount {
    public enum CompositionType {
        ADDITION,
        MAX
    }

    protected final int discountId;
    protected final double discountPercentage;
    protected final CompositionType compositionType;
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

    public int getDiscountId() {
        return discountId;
    }

    public abstract boolean checkApplies(Product p);

    public double calculateNewPercentage(double currentPercentage) {
        return switch (compositionType) {
            case ADDITION -> Math.min(currentPercentage + discountPercentage, 1.0);
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
