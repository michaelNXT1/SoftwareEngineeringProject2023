package DAOs;

import BusinessLayer.Product;
import BusinessLayer.Store;
import Repositories.IMapProductIntegerRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;

public class MapProductIntegerDAO implements IMapProductIntegerRepository {
    private Map<Product, Integer> productMap;
    private Store store;

    public MapProductIntegerDAO(Map<Product, Integer> productMap, Store store) {
        this.productMap = productMap;
        this.store = store;
    }

    @Override
    public void addProduct(Product product, int quantity) {

    }

    @Override
    public void removeProduct(Product product) {

    }

    // Helper method to update the store entity
    private void updateStore(Store store) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(store); // Update the store entity in the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateProductQuantity(Product product, int quantity) {

    }

    @Override
    public Map<Product, Integer> getAllProducts() {
        return productMap;
    }

    @Override
    public int getProductQuantity(Product product) {
        return 1;
    }

    @Override
    public boolean containsProduct(Product product) {
        return true;
    }
}
