package BusinessLayer.Discounts;

import BusinessLayer.Product;
import ServiceLayer.DTOs.Discounts.CategoryDiscountDTO;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import javax.persistence.*;

@Entity
@Table(name = "category_discounts")
public class CategoryDiscount extends Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category")
    private final String category;

    public CategoryDiscount(int discountId, double discountPercentage, String category, int compositionType) throws Exception {
        super(discountId, discountPercentage, compositionType);
        this.category = category;
        this.id = 0L; // Initializing with a default value
    }

    public CategoryDiscount() {
        super();
        category = null;
        this.id = 0L; // Initializing with a default value
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
