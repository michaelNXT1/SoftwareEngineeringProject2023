package BusinessLayer.Repositories.Interfaces;

import java.util.Map;

public interface IProductListRepository {
    void addProduct(int productId, int quantity);
    void removeProduct(int productId);
    void updateProductQuantity(int productId, int quantity);
    int getProductQuantity(int productId);
    Map<Integer, Integer> getAllProducts();
}
