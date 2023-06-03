package DAOs;

import BusinessLayer.Product;
import BusinessLayer.Store;
import BusinessLayer.Repositories.IMapProductIntegerRepository;
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
        Map<Product, Integer> productMap = store.getProducts().getAllProducts();
        productMap.put(product, quantity);
        store.setProducts(new MapProductIntegerDAO(productMap,store));
        updateStore(store);
    }

    @Override
    public void removeProduct(Product product) {
        Map<Product, Integer> productMap = store.getProducts().getAllProducts();
        productMap.remove(product);
        store.setProducts(new MapProductIntegerDAO(productMap,store));
        updateStore(store);
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
        Map<Product, Integer> productMap = store.getProducts().getAllProducts();
        productMap.put(product, quantity);
        store.setProducts(new MapProductIntegerDAO(productMap,store));
        updateStore(store);
    }

    @Override
    public Map<Product, Integer> getAllProducts() {
        return productMap;
    }

    @Override
    public int getProductQuantity(Product product) {
        Map<Product, Integer> productMap = store.getProducts().getAllProducts();
        return productMap.getOrDefault(product, 0);
    }

    @Override
    public boolean containsProduct(Product product) {
        Map<Product, Integer> productMap = store.getProducts().getAllProducts();
        return productMap.containsKey(product);
    }
}
