package DAOs;

import BusinessLayer.SystemManager;
import Repositories.IMapStringSystemManagerRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapStringSystemManagerDAO implements IMapStringSystemManagerRepository {

    @Override
    public void addSystemManager(String key, SystemManager systemManager) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(systemManager);
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
    public void removeSystemManager(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SystemManager systemManager = session.get(SystemManager.class, key);
            if (systemManager != null) {
                session.remove(systemManager);
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

}
