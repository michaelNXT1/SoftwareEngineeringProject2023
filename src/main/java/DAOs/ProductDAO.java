package DAOs;

import BusinessLayer.Product;
import Repositories.IProductRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProductDAO implements IProductRepository {

    public void saveProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public Product getProductById(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Product product = null;
        try {
            product = session.get(Product.class, productId);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return product;
    }

    public List<Product> getAllProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList = null;
        try {
            productList = session.createQuery("FROM products", Product.class).list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return productList;
    }

    @Override
    public void addAllProducts(List<Product> productList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (Product product : productList) {
                session.persist(product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}