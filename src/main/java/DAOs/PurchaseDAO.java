package DAOs;

import BusinessLayer.Purchase;
import BusinessLayer.ShoppingBag;
import Repositories.IPurchaseRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PurchaseDAO implements IPurchaseRepository {

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Purchase").executeUpdate();
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

    public void savePurchase(Purchase purchase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(purchase);
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

    public Purchase getPurchaseById(Long id) {
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

    @Override
    public List<Purchase> getAllPurchases() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Purchase> purchaseList = null;
        try {
            purchaseList = session.createQuery("FROM purchases", Purchase.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return purchaseList;
    }

    @Override
    public void removePurchase(Purchase purchase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(purchase);
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

    // Add other methods as needed
}