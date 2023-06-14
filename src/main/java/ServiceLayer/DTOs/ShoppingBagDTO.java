package ServiceLayer.DTOs;

import BusinessLayer.Product;
import BusinessLayer.Purchase;
import BusinessLayer.ShoppingBag;
import BusinessLayer.ShoppingCart;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShoppingBagDTO {
    public StoreDTO getStore() {
        return store;
    }

    private final StoreDTO store;

    public Map<ProductDTO, Integer> getProductList() {
        return productList;
    }

    private final Map<ProductDTO, Integer> productList;

    public ShoppingBagDTO(ShoppingBag shoppingBag) {
        productList = new HashMap<>();
        this.store = new StoreDTO(shoppingBag.getStore());
        for (Product product : shoppingBag.getProductList()) {
            ProductDTO productDTO = new ProductDTO(product);
            productList.put(productDTO, product.getAmount());
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
