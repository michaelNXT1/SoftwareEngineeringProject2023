package DAOs;

import BusinessLayer.SystemManager;
import Repositories.IMapStringSystemManagerRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapStringSystemManagerDAO implements IMapStringSystemManagerRepository {

    private Map<String,SystemManager> sessionToSystemManager = new HashMap<>();

    @Override
    public void updateSystemManager(SystemManager systemManager) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(systemManager);
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
    public void addSystemManager(String key, SystemManager systemManager) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if(getSystemManager(key) == null) {
                sessionToSystemManager.put(key, systemManager);
                session.persist(systemManager);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void removeSystemManager(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SystemManager systemManager = session.get(SystemManager.class, key);
            if (systemManager != null) {
                session.remove(systemManager);
                sessionToSystemManager.remove(key);
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
    public SystemManager getSystemManager(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SystemManager systemManager = null;
        try {
            if(sessionToSystemManager.containsKey(key))
                systemManager = sessionToSystemManager.get(key);
            else
                systemManager = session.get(SystemManager.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return systemManager;
    }

    @Override
    public Map<String, SystemManager> getAllSystemManagers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Map<String, SystemManager> systemManagerMap = null;
        try {
            systemManagerMap = session.createQuery("FROM SystemManager", SystemManager.class).getResultStream()
                    .collect(Collectors.toMap(SystemManager::getUsername, Function.identity()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return systemManagerMap;
    }

    @Override
    public boolean containsValue(SystemManager sm) {
        Map<String, SystemManager> systemManagerMap = getAllSystemManagers();
        return systemManagerMap.containsValue(sm);
    }

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM SystemManager").executeUpdate();
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
