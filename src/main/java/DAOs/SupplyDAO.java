package DAOs;

import BusinessLayer.SupplyDetails;
import Repositories.ISupplyRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SupplyDAO implements ISupplyRepo {

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM SupplyDetails ").executeUpdate();
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

    @Override
    public void addSupply(SupplyDetails supplyDetails) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(supplyDetails);
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
    public void removeSupply(SupplyDetails supplyDetails) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(supplyDetails);
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
    public SupplyDetails getSupply(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SupplyDetails supplyDetails = null;
        try {
            supplyDetails = session.get(SupplyDetails.class, key);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return supplyDetails;
    }

    @Override
    public List<SupplyDetails> getAllSupply() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<SupplyDetails> supplyDetails = null;
        try {
            String hql = "FROM SupplyDetails";
            Query<SupplyDetails> query = session.createQuery(hql, SupplyDetails.class);
            supplyDetails = query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return supplyDetails;
    }

    @Override
    public void updateSupply(SupplyDetails supplyDetails) {

    }
}
