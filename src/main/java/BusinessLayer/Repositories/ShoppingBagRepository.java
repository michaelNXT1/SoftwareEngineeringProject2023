package BusinessLayer.Repositories;

import BusinessLayer.Repositories.Interfaces.IShoppingBagRepository;
import BusinessLayer.ShoppingBag;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBagRepository implements IShoppingBagRepository {
    private final List<ShoppingBag> shoppingBags;

    public ShoppingBagRepository() {
        this.shoppingBags = new ArrayList<>();
    }

    @Override
    public void addShoppingBag(ShoppingBag shoppingBag) {
        shoppingBags.add(shoppingBag);
    }

    @Override
    public void removeShoppingBag(ShoppingBag shoppingBag) {
        shoppingBags.remove(shoppingBag);
    }

    @Override
    public ShoppingBag getShoppingBagById(int id) {
        for (ShoppingBag shoppingBag : shoppingBags) {
            if (shoppingBag.getId() == id) {
                return shoppingBag;
            }
        }
        return null;
    }

    @Override
    public List<ShoppingBag> getAllShoppingBags() {
        return shoppingBags;
    }
}
