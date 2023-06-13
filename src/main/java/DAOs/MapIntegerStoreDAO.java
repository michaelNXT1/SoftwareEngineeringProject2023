package DAOs;

import BusinessLayer.Store;
import Repositories.IMapIntegerStoreRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapIntegerStoreDAO implements IMapIntegerStoreRepository {

    @Override
    public void addStore(Integer key, Store store) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(store);
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
    public void removeStore(Integer key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Store store = session.get(Store.class, key);
            if (store != null) {
                session.remove(store);
            }
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
    public Store getStore(Integer key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Store store = null;
        try {
            store = session.get(Store.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return store;
    }

    @Override
    public Map<Integer, Store> getAllStores() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Map<Integer, Store> storeMap = null;
        try {
            storeMap = session.createQuery("FROM Store", Store.class).getResultStream()
                    .collect(Collectors.toMap(Store::getStoreId, Function.identity()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return storeMap;
    }
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Store").executeUpdate();
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
