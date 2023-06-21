package application.views;


import CommunicationLayer.MarketController;
import ServiceLayer.ResponseT;
import application.components.AppNav;
import application.components.AppNavItem;
import application.views.about.AboutView;
import application.views.category.CategoryView;
import application.views.openStore.OpenStore;
import application.views.registration.RegistrationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import org.vaadin.lineawesome.LineAwesomeIcon;

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
//        getElement().executeJs("document.documentElement.setAttribute('theme', $0)", Lumo.DARK);
        sessionId = marketController.enterMarket().value;
        addToNavbar(createHeaderContent());
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        MainLayout.sessionId = sessionId;
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

        var themeToggle = new Checkbox("Dark theme");
        themeToggle.addValueChangeListener(e -> getElement().executeJs("document.documentElement.setAttribute('theme', $0)", e.getValue() ? Lumo.DARK : Lumo.LIGHT));
        themeToggle.setValue(true);

        loginButton = new Button("Login", e -> loginDialog());
        signUpButton = new Button("Sign Up", e -> UI.getCurrent().navigate(RegistrationView.class));
        logoutButton = new Button("Logout", e -> logout());
        boolean isLoggedIn = marketController.isLoggedIn(sessionId).value;
        loginButton.setVisible(!isLoggedIn);
        signUpButton.setVisible(!isLoggedIn);
        logoutButton.setVisible(isLoggedIn);

        Div div1 = new Div(), div2 = new Div(), div3 = new Div();
        leftLayout.add(appName);
        centerLayout.add(div1, homeButton, select, searchBox, searchType, searchButton, cartButton, themeToggle, div2);
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

    private void loginDialog() {
        Dialog dialog = new Dialog();
        TextField usernameField;
        PasswordField passwordField;
        Button submitButton;
        Header lheader = new Header();
        lheader.setText("Login");
        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        submitButton = new Button("Login", e -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            ResponseT<String> r = marketController.login(username, password);
            MainLayout.setSessionId(r.value);
            if (r.getError_occurred())
                Notification.show(r.error_message, 3000, Notification.Position.MIDDLE);
            else {
                Notification.show("you logged in successfully", 3000, Notification.Position.MIDDLE);
                dialog.close();
                postLogin();
            }
        });
        marketController = MarketController.getInstance();

        dialog.add(new VerticalLayout(lheader, usernameField, passwordField, submitButton));
        dialog.open();
    }

    public void postLogin() {
        UI.getCurrent().navigate(AboutView.class);
        boolean isLoggedIn = marketController.isLoggedIn(sessionId).value;
        loginButton.setVisible(!isLoggedIn);
        signUpButton.setVisible(!isLoggedIn);
        logoutButton.setVisible(isLoggedIn);
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
        actionsMap.put("System Manager Login", () -> new SystemManagerPermissions().open());
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
        searchType.setValue("Contains keyword");
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