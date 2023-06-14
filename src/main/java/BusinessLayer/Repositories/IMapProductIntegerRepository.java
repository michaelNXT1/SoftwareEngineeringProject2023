package BusinessLayer.Repositories;

import BusinessLayer.Product;
import BusinessLayer.Store;

import java.util.Map;

public interface IMapProductIntegerRepository {
    void addProduct(Product product, int quantity);

    void removeProduct(Product product);

    void updateProductQuantity(Product product, int quantity);

    Map<Product, Integer> getAllProducts();

    int getProductQuantity(Product product);

    boolean containsProduct(Product product);
}

