package application.views;


import CommunicationLayer.MarketController;
import ServiceLayer.ResponseT;
import application.components.AppNav;
import application.components.AppNavItem;
import application.views.about.AboutView;
import application.views.category.CategoryView;
import application.views.helloworld.HelloWorldView;
import application.views.login.LoginView;
import application.views.openStore.OpenStore;
import application.views.registration.RegistrationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {
    private static String sessionId;
    private final Map<String, Runnable> searchActionMap = new HashMap<>();
    MarketController marketController = MarketController.getInstance();
    private TextField searchBox;
    private Select<String> searchType;
    private Button loginButton;
    private Button signUpButton;
    private Button logoutButton;

    public MainLayout() {
        sessionId = marketController.enterMarket().value;
        config();
        addToNavbar(createHeaderContent());
        addDrawerContent();
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        MainLayout.sessionId = sessionId;
    }

    private void config() {
        marketController.signUp("Shoham", "1234");
        marketController.signUp("Alon", "1234");
        marketController.signUp("Shani", "1234");
        marketController.signUp("Idan", "1234");

        marketController.signUp("Michael", "1234");
        sessionId = marketController.login("Michael", "1234").value;
        marketController.addPaymentMethod(sessionId, "a", "a", "a", "a");
        marketController.addSupplyDetails(sessionId, "a", "a", "a", "a", "a");
        int store_id = marketController.openStore(sessionId, "Shufersal").value;
        marketController.openStore(sessionId, "Ebay");
        Map<String, Integer> productMap = new HashMap<>();
        productMap.put("Klik Marbles", marketController.addProduct(sessionId, store_id, "Klik Marbles", 6.8, "Snacks", 50, "").value.getProductId());
        productMap.put("Banana", marketController.addProduct(sessionId, store_id, "Banana", 7.9, "Fruit", 50, "").value.getProductId());
        productMap.put("Bread", marketController.addProduct(sessionId, store_id, "Bread", 10.0, "Pastries", 50, "").value.getProductId());
        productMap.put("Watermelon", marketController.addProduct(sessionId, store_id, "Watermelon", 35.4, "Fruit", 50, "").value.getProductId());
        productMap.put("Milk", marketController.addProduct(sessionId, store_id, "Milk", 6.75, "Dairy", 50, "").value.getProductId());
        productMap.put("Bamba", marketController.addProduct(sessionId, store_id, "Bamba", 4.3, "Snacks", 50, "").value.getProductId());
        productMap.put("Yogurt", marketController.addProduct(sessionId, store_id, "Yogurt", 5.3, "Dairy", 50, "").value.getProductId());
        productMap.put("Lay's", marketController.addProduct(sessionId, store_id, "Lay's", 4.0, "Snacks", 50, "").value.getProductId());
        productMap.put("Apple", marketController.addProduct(sessionId, store_id, "Apple", 11.9, "Fruit", 50, "").value.getProductId());
        productMap.put("Bun", marketController.addProduct(sessionId, store_id, "Bun", 4.0, "Pastries", 50, "").value.getProductId());


        List<Long> discounts = new ArrayList<>();
        discounts.add(marketController.addProductDiscount(sessionId, store_id, productMap.get("Apple"), 0.1, 0).value);
        discounts.add(marketController.addCategoryDiscount(sessionId, store_id, "Dairy", 0.5, 0).value);
        discounts.add(marketController.addCategoryDiscount(sessionId, store_id, "Pastries", 0.05, 0).value);
        discounts.add(marketController.addStoreDiscount(sessionId, store_id, 0.2, 0).value);
        marketController.addMinBagTotalDiscountPolicy(sessionId, store_id, Math.toIntExact(discounts.get(0)), 200.0);
        marketController.addMinQuantityDiscountPolicy(sessionId, store_id, Math.toIntExact(discounts.get(2)), productMap.get("Bread"), 5, true);
        marketController.addMinQuantityDiscountPolicy(sessionId, store_id, Math.toIntExact(discounts.get(2)), productMap.get("Bun"), 5, true);

        marketController.addMaxQuantityPolicy(sessionId, store_id ,productMap.get("Apple"), 5);
        marketController.addCategoryTimeRestrictionPolicy(sessionId, store_id, "Snacks", LocalTime.of(7, 0, 0), LocalTime.of(23, 0, 0));

        marketController.setPositionOfMemberToStoreOwner(sessionId, store_id, "Alon");
        marketController.setPositionOfMemberToStoreManager(sessionId, store_id, "Shoham");
        marketController.addStoreManagerPermissions(sessionId, "Shoham", store_id, 4);

        marketController.addProductToCart(sessionId, store_id, productMap.get("Klik Marbles"), 4);
        marketController.addProductToCart(sessionId, store_id, productMap.get("Bread"), 4);

        marketController.logout(sessionId);

        sessionId = marketController.login("Alon", "1234").value;
        marketController.setPositionOfMemberToStoreOwner(sessionId, store_id, "Shani");
        marketController.logout(sessionId);

        sessionId = marketController.login("Michael", "1234").value;
//
//        sessionId = marketController.login("Shoham", "1234").value;
    }


    private void addDrawerContent() {
        H1 appName = new H1("Categories");
        appName.addClassNames(FontSize.SMALL, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createCategoryNev());

        addToDrawer(header, scroller, createFooter());
    }

    private Component createHeaderContent() {
        VerticalLayout vl = new VerticalLayout();
        vl.setWidthFull();
        vl.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        HorizontalLayout leftLayout = new HorizontalLayout();
        HorizontalLayout centerLayout = new HorizontalLayout();
        HorizontalLayout rightLayout = new HorizontalLayout();

        centerLayout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);
        rightLayout.addClassNames(Display.FLEX, AlignItems.END, Padding.Horizontal.LARGE);

        H1 appName = new H1("AIMSS incs.");
        appName.setWidthFull();

        Button homeButton = new Button("Home", e -> UI.getCurrent().navigate(AboutView.class));
        Select<String> select = initActionSelect();
        searchBox = new TextField();
        searchBox.setPlaceholder("Search product");
        searchType = initSearchSelect();
        Button searchButton = new Button("", VaadinIcon.SEARCH.create(), e -> SearchProducts());
        Button cartButton = new Button("Cart", VaadinIcon.CART.create(), e -> UI.getCurrent().navigate(ShoppingCart.class));

        loginButton = new Button("Login", e -> UI.getCurrent().navigate(LoginView.class));
        signUpButton = new Button("Sign Up", e -> UI.getCurrent().navigate(RegistrationView.class));
        logoutButton = new Button("Logout", e -> logout());
        boolean isLoggedIn = marketController.isLoggedIn(sessionId).value;
        loginButton.setVisible(!isLoggedIn);
        signUpButton.setVisible(!isLoggedIn);
        logoutButton.setVisible(isLoggedIn);

        Div div1 = new Div(), div2 = new Div(), div3 = new Div();
        leftLayout.add(appName);
        centerLayout.add(div1, homeButton, select, searchBox, searchType, searchButton, cartButton, div2);
        centerLayout.setFlexGrow(1, div1);
        centerLayout.setFlexGrow(1, div2);

        rightLayout.add(div3, loginButton, signUpButton, logoutButton);
        rightLayout.setFlexGrow(1, div3);
        layout.add(leftLayout, centerLayout, rightLayout);
        layout.setFlexGrow(1, leftLayout);
        layout.setFlexGrow(1, centerLayout);
        layout.setFlexGrow(1, rightLayout);
        vl.add(layout);
        return vl;
    }

    private void logout() {

        ResponseT<String> r = marketController.logout(sessionId);
        sessionId = r.value;
        if (r.getError_occurred())
            Notification.show(r.error_message, 3000, Notification.Position.MIDDLE);
        else {
            Notification.show("you logged out successfully", 3000, Notification.Position.MIDDLE);
            UI.getCurrent().navigate(AboutView.class);
            boolean isLoggedIn = marketController.isLoggedIn(sessionId).value;
            loginButton.setVisible(!isLoggedIn);
            signUpButton.setVisible(!isLoggedIn);
            logoutButton.setVisible(isLoggedIn);
        }

        if (sessionId != null)
            UI.getCurrent().navigate(AboutView.class);
    }

    private void SearchProducts() {
        if (searchType.getValue() != null) {
            searchActionMap.get(searchType.getValue()).run();
            UI.getCurrent().navigate(SearchResultView.class);
        }
    }

    private Select<String> initActionSelect() {
        Select<String> select = new Select<>();
        Map<String, Runnable> actionsMap = new HashMap<>();
        actionsMap.put("Add Payment Method", () -> UI.getCurrent().navigate(AddPaymentMethod.class));
        actionsMap.put("Add Delivery Address", () -> UI.getCurrent().navigate(AddDeliveryAddress.class));
        actionsMap.put("Open a New Store", () -> UI.getCurrent().navigate(OpenStore.class));
        actionsMap.put("My Stores", () -> UI.getCurrent().navigate(ManagerStoresView.class));
        actionsMap.put("My Purchases", () -> UI.getCurrent().navigate(UserPurchases.class));

        actionsMap.put("Get information about members", () -> UI.getCurrent().navigate(GetInformationAboutMembers.class));
        actionsMap.put("System Manager Registration", () -> UI.getCurrent().navigate(SignUpSystemManager.class));

        select.setItems(actionsMap.keySet().stream().sorted().collect(Collectors.toList()));
        select.addValueChangeListener(event -> {
            if (sessionId == null)
                Notification.show("Sorry, sessionId is null, please restart the server :(");
            else
                actionsMap.get(event.getValue()).run();
            select.clear();
        });
        select.setPlaceholder("Actions");
        return select;
    }

    private Select<String> initSearchSelect() {
        Select<String> searchType = new Select<>();
        searchActionMap.put("Exact match", () -> marketController.getProductsByName(MainLayout.getSessionId(), searchBox.getValue()));
        searchActionMap.put("Contains keyword", () -> marketController.getProductsBySubstring(MainLayout.getSessionId(), searchBox.getValue()));
        searchActionMap.put("Is of category", () -> marketController.getProductsByCategory(MainLayout.getSessionId(), searchBox.getValue()));
        searchType.setItems(searchActionMap.keySet().stream().toList().stream().sorted().collect(Collectors.toList()));
        searchType.setPlaceholder("Search type");
        return searchType;
    }

    private Footer createFooter() {

        return new Footer();
    }

    private AppNav createCategoryNev() {
        AppNav nav = new AppNav();

        List<String> catego = this.marketController.getAllCategories().value;
        for (String cat : catego) {
            nav.addItem(new AppNavItem(cat, CategoryView.class, LineAwesomeIcon.EMPIRE.create()));
        }
        return nav;
    }
}