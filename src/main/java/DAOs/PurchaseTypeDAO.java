package DAOs;

import BusinessLayer.PurchaseType;
import Repositories.IPurchaseTypeRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PurchaseTypeDAO implements IPurchaseTypeRepository {
    @Override
    public void savePurchaseType(PurchaseType product) {
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

    @Override
    public void deletePurchaseType(PurchaseType product) {
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

    @Override
    public PurchaseType getPurchaseTypeById(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        PurchaseType purchaseType = null;
        try {
            purchaseType = session.get(PurchaseType.class, productId);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return purchaseType;
    }

    @Override
    public List<PurchaseType> getAllPurchaseTypes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PurchaseType> purchaseTypeList = null;
        try {
            purchaseTypeList = session.createQuery("FROM PurchaseType", PurchaseType.class).list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return purchaseTypeList;
    }

    @Override
    public void clear() {

    }
}
