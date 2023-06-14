package DataAccessLayer.DAOs;

import BusinessLayer.PurchaseProduct;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PurchaseProductDAO {
    public void save(PurchaseProduct purchaseProduct) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(purchaseProduct);
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

    public void delete(PurchaseProduct purchaseProduct) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(purchaseProduct);
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

    public List<PurchaseProduct> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PurchaseProduct> purchaseProducts = null;
        try {
            purchaseProducts = session.createQuery("FROM PurchaseProduct", PurchaseProduct.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return purchaseProducts;
    }
}
