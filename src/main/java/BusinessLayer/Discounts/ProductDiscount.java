package BusinessLayer.Discounts;

import BusinessLayer.Product;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Discounts.ProductDiscountDTO;
import javax.persistence.*;

@Entity
@Table(name = "product_discounts")
public class ProductDiscount extends Discount {
    @Column(name = "product_id")
    private final int productId;

    public ProductDiscount(int discountId, double discountPercentage, int productId, int compositionType) throws Exception {
        super(discountId, discountPercentage, compositionType);
        this.productId = productId;
    }

    public ProductDiscount() {
        this.productId = 0; // Initializing with a default value
    }

    @Override
    public boolean checkApplies(Product p) {
        return p.getProductId() == productId;
    }

    @Override
    public DiscountDTO copyConstruct() {
        return new ProductDiscountDTO(this);
    }

    public int getProductId() {
        return productId;
    }
}
