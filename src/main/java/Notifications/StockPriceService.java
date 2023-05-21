package Notifications;

public class StockPriceService {

    private Map<String, Double> prices;
    private List<WebSocketSession> sessions;

    public StockPriceService() {
        this.prices = new HashMap<>();
        this.sessions = new ArrayList<>();
        this.prices.put("AAPL", 100.0);
    }

    public void updatePrice(String stock, double price) {
        if (prices.containsKey(stock)) {
            double oldPrice = prices.get(stock);
            if (price != oldPrice) {
                prices.put(stock, price);
                sendAlert(stock, price);
            }
        }
    }

    public void sendAlert(String stock, double price) {
        String message = String.format("Stock price for %s has changed to %.2f", stock, price);
        for (WebSocketSession session : sessions) {
            User user = getCurrentUser(session);
            if (user.getSubscriptions().contains(stock)) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException);
            }
        }
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    private User getCurrentUser(WebSocketSession session) {
        // Get current user based on session ID or authentication token
        return new User("john");
    }

}
    This code sets up a StockPriceService that keeps track of the current prices for each stock and a list of sessions that are subscribed to receive real-time alerts. When the updatePrice() method is called with a new price for a stock, it checks if the price has changed from the previous price and sends an alert to all subscribers for that stock.

        Implement a system for storing delayed alerts:
        java
        Copy code
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
    This code sets up an AlertService that keeps track of the delayed alerts for each stock. When a real-time alert is sent, the addAlert() method is called to store the alert message for that stock. When a user logs in and connects to the WebSocket, the getAlerts() method is called to retrieve any delayed alerts for the stocks that the user is subscribed to.

        Set up a Vaadin UI to display the alerts:
        java
        Copy code
public class TradingSystemUI extends VerticalLayout {

    private StockPriceService priceService;
    private AlertService alertService;

    public TradingSystemUI() {
        this.priceService = new StockPriceService();
        this.alertService = new AlertService();

        // Set up Vaadin UI components to display real-time and delayed alerts
        // ...

        // Connect to WebSocket and subscribe to AAPL stock
        UI.getCurrent().getPage().executeJs("var socket = new WebSocket('ws://' + window.location.host + '/stocks');");
        UI.getCurrent().getPage().executeJs("socket.onopen = function(event) { socket.send('subscribe AAPL'); };");

        // Add WebSocket message handler
        UI.getCurrent().getPage().addBrowserWindowResizeListener(event -> {
            UI.getCurrent().getPage().executeJs("socket.onmessage = function(event) { console.log(event.data); };");
        });

        // Add WebSocket session handler
        UI.getCurrent().getPage().addPopStateListener(event -> {
            priceService.addSession(null);
            alertService.getAlerts("AAPL").forEach(alert -> Notification.show(alert));
        });
    }

}
    This code sets up a Vaadin UI that connects to the WebSocket endpoint and subscribes to the AAPL stock. It also adds a message handler to receive real-time alerts and a session handler to retrieve delayed alerts when the user logs in. The UI can then display the alerts using Vaadin components such as Notification or custom UI components.





        User
        can you do it When a customer bought a product from their store, when the store
        closes or reopens or when their subscription is removed.
        ChatGPT
        Sure, here's an updated implementation for the real-time alerts based on the use cases you mentioned:

        Implement a service to keep track of store information:
        java
        Copy code
public class StoreService {

    private Map<String, Store> stores;

    public StoreService() {
        this.stores = new HashMap<>();
        stores.put("store1", new Store("store1", "Store 1", true, new Date(), new ArrayList<>()));
        stores.put("store2", new Store("store2", "Store 2", false, new Date(), new ArrayList<>()));
        stores.put("store3", new Store("store3", "Store 3", true, new Date(), new ArrayList<>()));
    }

    public void updateStore(String storeId, Store store) {
        stores.put(storeId, store);
    }

    public Store getStore(String storeId) {
        return stores.get(storeId);
    }

}
    This code sets up a StoreService that keeps track of store information, such as whether the store is open or closed and the subscription status. It also provides methods to update and retrieve the store information.

        Implement a WebSocket endpoint to send real-time alerts:
        java
        Copy code
@ServerEndpoint(value = "/stores")
public class StoreWebSocketEndpoint {

    private Set<WebSocketSession> sessions = new HashSet<>();

    private StoreService storeService;

    public StoreWebSocketEndpoint() {
        this.storeService = new StoreService();
    }

    @OnOpen
    public void onOpen(WebSocketSession session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(WebSocketSession session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Parse message to determine which store to update
        JsonObject jsonMessage = Json.createReader(new StringReader(message)).readObject();
        String storeId = jsonMessage.getString("storeId");
        String type = jsonMessage.getString("type");

        // Update store information
        Store store = storeService.getStore(storeId);
        switch (type) {
            case "OPEN":
                store.setOpen(true);
                break;
            case "CLOSE":
                store.setOpen(false);
                break;
            case "SUBSCRIPTION_REMOVED":
                store.setSubscribed(false);
                break;
            case "PRODUCT_SOLD":
                store.addSale(new Sale(new Date(), "Customer 1", "Product 1", 100.0));
                break;
        }
        storeService.updateStore(storeId, store);

        // Send real-time alert to subscribed sessions
        sessions.forEach(s -> {
            User user = getCurrentUser(s);
            if (user != null && store.isSubscribed(user.getUsername())) {
                try {
                    s.getBasicRemote().sendText(getAlertMessage(store, type));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private User getCurrentUser(WebSocketSession session) {
        // Get current user based on session ID or authentication token
        return new User("john");
    }

    private String getAlertMessage(Store store, String type) {
        // Generate alert message based on type of alert
        switch (type) {
            case "OPEN":
                return "Store " + store.getName() + " has opened!";
            case "CLOSE":
                return "Store " + store.getName() + " has closed!";
            case "SUBSCRIPTION_REMOVED":
                return "Your subscription to " + store.getName() + " has been removed.";
            case "PRODUCT_SOLD":
                Sale sale = store.getLatestSale();
                return "A new sale has been made at " + store.getName() + ": " +
                        sale




            User
            how can I connect it to a bussiness layer?
                    ChatGPT
            To connect the WebSocket endpoint to a business layer, you can create a separate service layer that handles business logic and interacts with the WebSocket endpoint.

            Here's an example implementation of a service layer:

            java
            Copy code
            public class StoreSubscriptionService {

                private StoreWebSocketEndpoint storeWebSocketEndpoint;
                private StoreService storeService;

                public StoreSubscriptionService() {
                    this.storeWebSocketEndpoint = new StoreWebSocketEndpoint();
                    this.storeService = new StoreService();
                }

                public void subscribeToStore(String storeId, String username) {
                    // Update subscription status in store information
                    Store store = storeService.getStore(storeId);
                    store.addSubscriber(username);
                    storeService.updateStore(storeId, store);

                    // Send confirmation message to user
                    User user = new User(username);
                    try {
                        storeWebSocketEndpoint.sendAlert(user, getSubscriptionConfirmationMessage(store));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public void unsubscribeFromStore(String storeId, String username) {
                    // Update subscription status in store information
                    Store store = storeService.getStore(storeId);
                    store.removeSubscriber(username);
                    storeService.updateStore(storeId, store);

                    // Send confirmation message to user
                    User user = new User(username);
                    try {
                        storeWebSocketEndpoint.sendAlert(user, getUnsubscriptionConfirmationMessage(store));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public List<Store> getStores() {
                    // Retrieve list of stores from StoreService
                    return new ArrayList<>(storeService.getStores().values());
                }

                private String getSubscriptionConfirmationMessage(Store store) {
                    // Generate subscription confirmation message
                    return "You have subscribed to alerts from " + store.getName() + ".";
                }

                private String getUnsubscriptionConfirmationMessage(Store store) {
                    // Generate unsubscription confirmation message
                    return "You have unsubscribed from alerts from " + store.getName() + ".";
                }

            }





