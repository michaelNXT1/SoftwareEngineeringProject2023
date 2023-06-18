package Repositories;

import BusinessLayer.Discounts.Discount;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface IDiscountRepo {
    void addDiscount(Discount notification);
    void removeDiscount(Discount notification);

    Discount get(Long id);
    void clear();
    List<Discount> getAllDiscounts();
}
