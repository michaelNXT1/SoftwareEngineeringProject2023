package Repositories;

import BusinessLayer.ShoppingBag;

import java.util.List;

public interface IShoppingBagRepository {
    void addShoppingBag(ShoppingBag shoppingBag);
    void removeShoppingBag(ShoppingBag shoppingBag);
    ShoppingBag getShoppingBagById(int id);
    List<ShoppingBag> getAllShoppingBags();
    void clearShoppingBags();
}