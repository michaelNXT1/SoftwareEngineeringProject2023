package BusinessLayer.Repositories.Interfaces;

import BusinessLayer.SystemManager;

import java.util.Map;

public interface ISystemManagerRepository {
    void addSystemManager(String key, SystemManager systemManager);
    void removeSystemManager(String key);
    SystemManager getSystemManager(String key);
    Map<String, SystemManager> getAllSystemManagers();
    // Add other methods as needed
}
