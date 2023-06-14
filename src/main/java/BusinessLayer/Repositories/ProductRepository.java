package BusinessLayer.Repositories;


import BusinessLayer.Product;
import BusinessLayer.Repositories.Interfaces.IProductRepository;
import DataAccessLayer.DAOs.ProductDAO;
import java.util.List;

public class ProductRepository implements IProductRepository {

    private ProductDAO productDAO;

    public ProductRepository() {
        this.productDAO = new ProductDAO(); // Instantiate the ProductDAO class
    }

    public void saveProduct(Product product) {
        productDAO.save(product); // Call the save method of the ProductDAO class
    }

    public void deleteProduct(Product product) {
        productDAO.delete(product); // Call the delete method of the ProductDAO class
    }

    public Product getProductById(int productId) {
        return productDAO.getById(productId); // Call the getById method of the ProductDAO class
    }

    public List<Product> getAllProducts() {
        return productDAO.getAll(); // Call the getAll method of the ProductDAO class
    }
}




