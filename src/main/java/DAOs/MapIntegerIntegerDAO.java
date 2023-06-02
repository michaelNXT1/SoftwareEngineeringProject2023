package DAOs;

import BusinessLayer.Repositories.IMapIntegerIntegerRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;

public class MapIntegerIntegerDAO implements IMapIntegerIntegerRepository {

    private Map<Integer, Integer> integerIntegerMap;

    public MapIntegerIntegerDAO(Map<Integer, Integer> integerIntegerMap) {
        this.integerIntegerMap = integerIntegerMap;
    }

    @Override
    public void put(Integer key, Integer value) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(value);
            transaction.commit();
            integerIntegerMap.put(key, value);
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
    public Integer get(Integer key) {
        return integerIntegerMap.get(key);
    }

    @Override
    public void remove(Integer key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer val = integerIntegerMap.get(key);
            if (val != null) {
                session.remove(val);
                integerIntegerMap.remove(key);
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
    public boolean containsKey(Integer key) {
        return integerIntegerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Integer value) {
        return integerIntegerMap.containsValue(value);
    }

    @Override
    public Map<Integer, Integer> getAllItems() {
        return integerIntegerMap;
    }
}
