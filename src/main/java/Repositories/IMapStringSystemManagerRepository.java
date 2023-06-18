package Repositories;

import BusinessLayer.Product;
import BusinessLayer.SystemManager;

import java.util.Map;

public interface IMapStringSystemManagerRepository {
    void updateSystemManager(SystemManager systemManager);

    void addSystemManager(String key, SystemManager systemManager);
    void removeSystemManager(String key);
    SystemManager getSystemManager(String key);
    Map<String, SystemManager> getAllSystemManagers();
    boolean containsValue(SystemManager sm);

    void clear();
}
