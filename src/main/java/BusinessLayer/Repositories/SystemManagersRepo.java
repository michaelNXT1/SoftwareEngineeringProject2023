package BusinessLayer.Repositories;

import BusinessLayer.SystemManager;

import java.util.Map;

public class SystemManagersRepo {
    private Map<String, SystemManager> systemManagers;

    public Map<String, SystemManager> getSystemManagers() {
        return systemManagers;
    }

    public void addSystemManager(String name, SystemManager systemManager){
        systemManagers.put(name, systemManager);
    }

    public SystemManagersRepo(Map<String, SystemManager> systemManagers) {
        this.systemManagers = systemManagers;
    }
}
