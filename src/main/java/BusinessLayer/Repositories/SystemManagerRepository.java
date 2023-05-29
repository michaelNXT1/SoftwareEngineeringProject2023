package BusinessLayer.Repositories;

import BusinessLayer.Repositories.Interfaces.ISystemManagerRepository;
import BusinessLayer.SystemManager;

import java.util.HashMap;
import java.util.Map;

public class SystemManagerRepository implements ISystemManagerRepository {
    private final Map<String, SystemManager> systemManagers;

    public SystemManagerRepository() {
        this.systemManagers = new HashMap<>();
    }

    @Override
    public void addSystemManager(String key, SystemManager systemManager) {
        systemManagers.put(key, systemManager);
    }

    @Override
    public void removeSystemManager(String key) {
        systemManagers.remove(key);
    }

    @Override
    public SystemManager getSystemManager(String key) {
        return systemManagers.get(key);
    }

    @Override
    public Map<String, SystemManager> getAllSystemManagers() {
        return systemManagers;
    }
    // Implement other methods as needed
}






