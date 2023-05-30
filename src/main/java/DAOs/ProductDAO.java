package DAOs;

import DataAccessLayer.DAOs.HibernateUtil;
import BusinessLayer.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProductDAO{

    public void save(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(product);
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

    public void delete(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(product);
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

    public Product getById(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Product product = null;
        try {
            product = session.get(Product.class, productId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }

    public List<Product> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList = null;
        try {
            productList = session.createQuery("FROM products", Product.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productList;
    }
}