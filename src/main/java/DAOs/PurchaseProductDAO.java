package DAOs;

import BusinessLayer.PurchaseProduct;
import Repositories.IPurchaseProductRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class PurchaseProductDAO implements IPurchaseProductRepository {
    public void addPurchaseProduct(PurchaseProduct purchaseProduct) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(purchaseProduct);
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

    public void removePurchaseProduct(PurchaseProduct purchaseProduct) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.remove(purchaseProduct);
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


    public List<PurchaseProduct> getAllPurchaseProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PurchaseProduct> purchaseProducts = null;
        try {
            purchaseProducts = session.createQuery("FROM PurchaseProduct", PurchaseProduct.class).list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return purchaseProducts;
    }

    @Override
    public void addAllPurchaseProducts(List<PurchaseProduct> purchaseProducts) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (PurchaseProduct purchaseProduct : purchaseProducts) {
                session.persist(purchaseProduct);
            }
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

}