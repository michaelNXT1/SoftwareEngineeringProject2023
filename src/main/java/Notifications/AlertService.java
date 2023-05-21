package Notifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertService {

    private Map<String, List<String>> alerts;

    public AlertService() {
        this.alerts = new HashMap<>();
    }

    public void addAlert(String stock, String message) {
        if (alerts.containsKey(stock)) {
            alerts.get(stock).add(message);
        } else {
            List<String> stockAlerts = new ArrayList<>();
            stockAlerts.add(message);
            alerts.put(stock, stockAlerts);
        }
    }

    public List<String> getAlerts(String stock) {
        if (alerts.containsKey(stock)) {
            List<String> stockAlerts = alerts.get(stock);
            alerts.remove(stock);
            return stockAlerts;
        } else {
            return new ArrayList<>();
        }
    }

}
