package DataAccessLayer.DAOs;

import BusinessLayer.Store;
import DataAccessLayer.DAOs.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StoreDAO {

    public void save(Store store) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(store);
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

    public void delete(int storeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Store store = session.get(Store.class, storeId);
            if (store != null) {
                session.delete(store);
            }
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

    public Store getById(int storeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Store store = null;
        try {
            store = session.get(Store.class, storeId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return store;
    }

    // Add other methods as needed
}

