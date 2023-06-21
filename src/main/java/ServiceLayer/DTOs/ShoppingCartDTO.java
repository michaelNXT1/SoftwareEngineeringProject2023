package ServiceLayer.DTOs;

import BusinessLayer.ShoppingBag;
import BusinessLayer.ShoppingCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartDTO {
    public List<ShoppingBagDTO> shoppingBags;

    public ShoppingCartDTO(ShoppingCart shoppingCart) {
        List<ShoppingBagDTO> list = new ArrayList<>();
        for (ShoppingBag shoppingBag : shoppingCart.shoppingBags.getAllShoppingBags().stream().filter(sb->(sb.getShoppingCartId() == null && shoppingCart.getId() == null) || (sb.getShoppingCartId().equals(shoppingCart.getId()))).toList()) {
            ShoppingBagDTO shoppingBagDTO = new ShoppingBagDTO(shoppingBag);
            list.add(shoppingBagDTO);
        }
        if(shoppingCart.getShoppingBags2() != null && !shoppingCart.getShoppingBags2().isEmpty()){
            for(ShoppingBag sb : shoppingCart.getShoppingBags2())
                list.add(new ShoppingBagDTO(sb));
        }
        this.shoppingBags = list;
    }

    @Override
    public String toString() {
        StringBuilder cart = new StringBuilder();
        for (ShoppingBagDTO s : this.shoppingBags) {
            cart.append(s.toString() + "\n");
        }
        return cart.toString();
    }

    public Map<ProductDTO, Integer> getProducts() {
        Map<ProductDTO, Integer> productDTOList = new HashMap<>();
        for (ShoppingBagDTO shoppingBag : shoppingBags) {
            for (ProductDTO productDTO : shoppingBag.getProductList().keySet()) {
                productDTOList.put(productDTO, shoppingBag.getProductList().get(productDTO));
            }
        }
        return productDTOList;
    }


}
