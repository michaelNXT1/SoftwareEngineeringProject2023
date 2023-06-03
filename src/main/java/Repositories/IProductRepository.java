package Repositories;

import BusinessLayer.Product;

import java.util.List;

public interface IProductRepository {
    public void saveProduct(Product product);
    public void deleteProduct(Product product);
    public Product getProductById(int productId);
    public List<Product> getAllProducts();
    void addAllProducts(List<Product> productList);

}