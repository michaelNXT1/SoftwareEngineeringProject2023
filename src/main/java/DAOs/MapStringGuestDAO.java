package DAOs;

import BusinessLayer.Guest;
import Repositories.IMapStringGuestRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Map;

public class MapStringGuestDAO implements IMapStringGuestRepository {

    private Map<String, Guest> guestMap;

    public MapStringGuestDAO(Map<String, Guest> guestMap) {
        this.guestMap = guestMap;
    }

    @Override
    public void put(String key, Guest guest) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(guest);
            transaction.commit();
            guestMap.put(key, guest);
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
    public Guest get(String key) {
        return guestMap.get(key);
    }

    @Override
    public void remove(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Guest guest = guestMap.get(key);
            if (guest != null) {
                session.remove(guest);
                guestMap.remove(key);
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
    public boolean containsKey(String key) {
        return guestMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Guest guest) {
        return guestMap.containsValue(guest);
    }

    @Override
    public Map<String, Guest> getAllGuests() {
        return guestMap;
    }
}
