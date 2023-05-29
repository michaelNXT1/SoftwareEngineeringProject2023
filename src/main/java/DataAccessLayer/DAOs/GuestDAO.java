package DataAccessLayer.DAOs;

import BusinessLayer.Guest;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestDAO {
    public void saveOrUpdate(String sessionId, Guest guest) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(guest);
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

    public void delete(String sessionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Guest guest = session.get(Guest.class, sessionId);
            if (guest != null) {
                session.delete(guest);
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

    public Guest getBySessionId(String sessionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Guest guest = null;
        try {
            guest = session.get(Guest.class, sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return guest;
    }

    public Map<String, Guest> getAll() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Guest> guests = null;
        try {
            guests = session.createQuery("FROM Guest", Guest.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        Map<String, Guest> userNameGuestMap = new HashMap<>();//maybe we need the sessionID instead of user
        if (guests != null) {
            for (Guest guest : guests) {
                userNameGuestMap.put(guest.getUsername(), guest);
            }
        }
        return userNameGuestMap;
    }
}
