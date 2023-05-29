package BusinessLayer.DAOsServices;

import BusinessLayer.Product;
import DataAccessLayer.DAOs.ProductDAO;
public class ProductService {
    private ProductDao productDao;

    public ProductService() {
        productDao = new ProductDao();
    }

    public void addProduct(Product product) {
        // Additional business logic or validation before saving the product
        productDao.addProduct(product);
    }

    public void updateProduct(Product product) {
        // Additional business logic or validation before updating the product
        productDao.updateProduct(product);
    }
}

