package DataAccessLayer.DAOs;

import BusinessLayer.SystemManager;
import DataAccessLayer.DAOs.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SystemManagerDAO {

    public void save(SystemManager systemManager) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(systemManager);
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

    public void delete(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SystemManager systemManager = session.get(SystemManager.class, key);
            if (systemManager != null) {
                session.delete(systemManager);
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

    public SystemManager getByKey(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SystemManager systemManager = null;
        try {
            systemManager = session.get(SystemManager.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return systemManager;
    }

    // Add other methods as needed
}
