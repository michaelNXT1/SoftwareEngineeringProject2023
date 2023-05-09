package ServiceLayer.DTOs;

import BusinessLayer.Product;
import BusinessLayer.Purchase;
import BusinessLayer.ShoppingBag;
import BusinessLayer.ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class ShoppingBagDTO {
    private final StoreDTO store;
    private final Map<ProductDTO, Integer> productList;

    public ShoppingBagDTO(ShoppingBag shoppingBag) throws Exception {
        productList = new HashMap<>();
        this.store = new StoreDTO(shoppingBag.getStore());
        for (Integer productId : shoppingBag.getProductList().keySet()) {
            ProductDTO productDTO = new ProductDTO(shoppingBag.getStore().getProduct(productId));
            productList.put(productDTO, shoppingBag.getProductList().get(productId));
        }
    }
    @Override
    public String toString(){
        StringBuilder shopping = new StringBuilder();
        for(Map.Entry<ProductDTO,Integer> ent:this.productList.entrySet()){
            shopping.append(ent.getKey().toString() + ": " + ent.getValue().toString()+"\n");
        }
        return shopping.toString();
    }
}
