package BusinessLayer.Discounts;

import BusinessLayer.Product;
import ServiceLayer.DTOs.Discounts.CategoryDiscountDTO;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
//import javax.persistence.*;
import jakarta.persistence.*;


@Entity
@Table(name = "category_discounts")
@DiscriminatorValue("CHILD")
public class CategoryDiscount extends Discount {
    @Column(name = "category")
    private final String category;

    public CategoryDiscount(int discountId, double discountPercentage, String category, int compositionType) throws Exception {
        super(discountId, discountPercentage, compositionType);
        this.category = category;
    }

    public CategoryDiscount() {
        super();
        category = null;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean checkApplies(Product p) {
        return p.getCategory().equals(category);
    }

    @Override
    public DiscountDTO copyConstruct() {
        return new CategoryDiscountDTO(this);
    }
}
