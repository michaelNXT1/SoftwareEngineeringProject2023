package DAOs;

import BusinessLayer.Discounts.Discount;
import Repositories.IDiscountRepo;

import java.util.concurrent.ConcurrentLinkedQueue;

public class DiscountDAO implements IDiscountRepo {
    @Override
    public void addDiscount(Discount notification) {

    }

    @Override
    public void removeDiscount(Discount notification) {

    }

    @Override
    public Discount get(Integer id) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public ConcurrentLinkedQueue<Discount> getAllDiscounts() {
        return null;
    }
}
