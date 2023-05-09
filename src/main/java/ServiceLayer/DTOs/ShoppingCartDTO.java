package ServiceLayer.DTOs;

import BusinessLayer.ShoppingBag;
import BusinessLayer.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {
    public List<ShoppingBagDTO> shoppingBags;

    public ShoppingCartDTO(ShoppingCart shoppingCart) throws Exception {
        List<ShoppingBagDTO> list = new ArrayList<>();
        for (ShoppingBag shoppingBag : shoppingCart.shoppingBags) {
            ShoppingBagDTO shoppingBagDTO = new ShoppingBagDTO(shoppingBag);
            list.add(shoppingBagDTO);
        }
        this.shoppingBags = list;
    }
}
