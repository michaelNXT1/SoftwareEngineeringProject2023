package DataAccessLayer.DAOs;

import BusinessLayer.Purchase;
import DataAccessLayer.DAOs.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PurchaseDAO {

    public void save(Purchase purchase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(purchase);
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

    public Purchase getById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Purchase purchase = null;
        try {
            purchase = session.get(Purchase.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return purchase;
    }
    // Add other methods as needed
}
