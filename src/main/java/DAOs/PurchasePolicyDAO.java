package DAOs;
import BusinessLayer.Policies.PurchasePolicies.BasePurchasePolicy;
import Repositories.IPurchasePolicyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PurchasePolicyDAO implements IPurchasePolicyRepository{
    private final SessionFactory sessionFactory;
    private final List<BasePurchasePolicy> purchasePolicies;

    public PurchasePolicyDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.purchasePolicies = new ArrayList<>();
    }

    @Override
    public void addPurchasePolicy(BasePurchasePolicy purchasePolicy) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(purchasePolicy);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            purchasePolicies.add(purchasePolicy);
        }
    }

    @Override
    public void removePurchasePolicy(BasePurchasePolicy purchasePolicy) {
        Session session = sessionFactory.openSession();
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
            purchasePolicies.remove(purchasePolicy);
        }
    }

    @Override
    public List<BasePurchasePolicy> getAllPurchasePolicies() {
        List<BasePurchasePolicy> purchasePolicies = new ArrayList<>();
        Session session = sessionFactory.openSession();

        try {
            Query<BasePurchasePolicy> query = session.createQuery("FROM BasePurchasePolicy", BasePurchasePolicy.class);
            purchasePolicies = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return purchasePolicies;
    }

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM PurchasePolicyOperation").executeUpdate();
            session.createQuery("DELETE FROM CategoryTimeRestrictionPurchasePolicy").executeUpdate();
            session.createQuery("DELETE FROM MaxQuantityPurchasePolicy").executeUpdate();
            session.createQuery("DELETE FROM MinQuantityPurchasePolicy").executeUpdate();
            session.createQuery("DELETE FROM ProductTimeRestrictionPurchasePolicy").executeUpdate();
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
}
