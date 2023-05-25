package DAOs;

import ServiceLayer.DTOs.ProductDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ProductDAO {

    private SessionFactory sessionFactory;

    public ProductDAO() {
        // Create the Hibernate configuration
        Configuration configuration = new Configuration().configure();

        // Build the session factory
        sessionFactory = configuration.buildSessionFactory();
    }

    public void addProduct(ProductDTO product) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Save or update the product
            session.saveOrUpdate(product);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateProduct(ProductDTO product) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Save or update the product
            session.saveOrUpdate(product);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}


