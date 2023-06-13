package Repositories;

import BusinessLayer.Discounts.Discount;
import Notification.Notification;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface IDiscountRepo {
    void addDiscount(Discount notification);
    void removeDiscount(Discount notification);

    Discount get(Integer id);
    void clear();
    ConcurrentLinkedQueue<Discount> getAllDiscounts();
}
