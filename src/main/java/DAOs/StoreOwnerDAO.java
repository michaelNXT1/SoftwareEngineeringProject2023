package DAOs;

import Repositories.IStoreOwnerRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;

public class StoreOwnerDAO implements IStoreOwnerRepository {
    private final List<String> storeOwners;
    public StoreOwnerDAO() {
        this.storeOwners = new LinkedList<>();
    }

    @Override
    public void addStoreOwner(String storeOwner) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(storeOwner);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            this.storeOwners.add(storeOwner);
        }
    }

    @Override
    public void removeStoreOwner(String storeOwner) {
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
            this.storeOwners.remove(storeOwner);
        }
    }

    @Override
    public List<String> getAllStoreOwners() {
        List<String> storeOwners = new LinkedList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<String> query = session.createQuery("FROM String", String.class);
            storeOwners = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return storeOwners;
    }
}