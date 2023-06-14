package Repositories;

import BusinessLayer.ShoppingCart;

import java.util.List;

public interface IShoppingCartRepo {
    void addShoppingCart(ShoppingCart shoppingCart);
    void removeShoppingBag(ShoppingCart shoppingCart);
    ShoppingCart getShoppingBagById(int id);
    List<ShoppingCart> getAllShoppingCart();
    void clear();
}
