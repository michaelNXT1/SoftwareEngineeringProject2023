package DataAccessLayer.DAOs;

import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PurchasePolicyDAO {
    public void saveOrUpdate(BasePurchasePolicy purchasePolicy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(purchasePolicy);
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

    public void delete(BasePurchasePolicy purchasePolicy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(purchasePolicy);
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

    public List<BasePurchasePolicy> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BasePurchasePolicy> purchasePolicies = null;
        try {
            purchasePolicies = session.createQuery("FROM BasePurchasePolicy", BasePurchasePolicy.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return purchasePolicies;
    }
}
