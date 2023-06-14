package BusinessLayer.Repositories;

import BusinessLayer.Repositories.Interfaces.IProductListRepository;

import java.util.HashMap;
import java.util.Map;

public class ProductListRepository implements IProductListRepository {
    private final Map<Integer, Integer> productList;

    public ProductListRepository() {
        this.productList = new HashMap<>();
    }

    @Override
    public void addProduct(int productId, int quantity) {
        productList.put(productId, quantity);
    }

    @Override
    public void removeProduct(int productId) {
        productList.remove(productId);
    }

    @Override
    public void updateProductQuantity(int productId, int quantity) {
        productList.put(productId, quantity);
    }

    @Override
    public int getProductQuantity(int productId) {
        return productList.getOrDefault(productId, 0);
    }

    @Override
    public Map<Integer, Integer> getAllProducts() {
        return productList;
    }
}
