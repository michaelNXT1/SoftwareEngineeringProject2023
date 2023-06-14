package DataAccessLayer.DAOs;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StoreOwnerDAO {
    public void saveOrUpdate(String storeOwner) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(storeOwner);
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

    public void delete(String storeOwner) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(storeOwner);
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

    public List<String> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<String> storeOwners = null;
        try {
            storeOwners = session.createQuery("SELECT storeOwner FROM StoreOwner", String.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return storeOwners;
    }
}
