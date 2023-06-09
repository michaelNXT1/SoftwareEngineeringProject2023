package ServiceLayer.DTOs;

import BusinessLayer.Product;
import BusinessLayer.ShoppingBag;

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
        for (Integer productId : shoppingBag.getProductListId().keySet()) {
            ProductDTO productDTO = new ProductDTO(Objects.requireNonNull(shoppingBag.getStore().getProducts().getAllProducts().stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null)));
            productList.put(productDTO, shoppingBag.getProductListId().get(productId));
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
