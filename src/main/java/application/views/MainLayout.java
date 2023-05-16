package application.views;


import CommunicationLayer.MarketController;
import application.components.AppNav;
import application.components.AppNavItem;
import application.views.about.AboutView;
import application.views.addStoreManger.AddStoreManager;
import application.views.addStoreOwner.AddStoreOwner;
import application.views.category.CategoryView;
import application.views.helloworld.HelloWorldView;
import application.views.login.LoginView;
import application.views.openStore.OpenStore;
import application.views.registration.RegistrationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
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

    MarketController marketController = MarketController.getInstance();
    private TextField searchBox;
    private Map<String, Runnable> searchActionMap = new HashMap<>();
    private Select<String> searchType;

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

    }

    public MainLayout() {
        sessionId = marketController.enterMarket();
        config();
        addToNavbar(createHeaderContent());
        addDrawerContent();
    }

    private void config() {
        marketController.signUp("Michael", "1234");
        sessionId = marketController.login("Michael", "1234");
        marketController.openStore(sessionId, "Amazon");
        marketController.openStore(sessionId, "Ebay");
        marketController.addProduct(sessionId, 0, "a man", 10.0, "people", 50, "just a man");
//        sessionId = marketController.logout(sessionId).value;
    }


    private void addDrawerContent() {
        H1 appName = new H1("Categories");
        appName.addClassNames(FontSize.SMALL, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createCategoryNev());

        addToDrawer(header, scroller, createFooter());
    }

    public static void setSessionId(String sessionId) {
        MainLayout.sessionId = sessionId;
    }

    public static String getSessionId() {
        return sessionId;
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

        H1 appName = new H1("AIMSS inc.");
        appName.setWidthFull();

        Button homeButton = new Button("Home", e -> UI.getCurrent().navigate(HelloWorldView.class));
        Button aboutButton = new Button("About", e -> UI.getCurrent().navigate(AboutView.class));
        Button optionsButton = new Button("Options");
        Select<String> select = initActionSelect();
        searchBox = new TextField();
        searchBox.setPlaceholder("Search product");
        searchType = initSearchSelect();
        Button searchButton = new Button("", VaadinIcon.SEARCH.create(), e -> SearchProducts());
        Button cartButton = new Button("Cart", VaadinIcon.CART.create(), e -> UI.getCurrent().navigate(ShoppingCart.class));

        Button loginButton = new Button("Login", e -> UI.getCurrent().navigate(LoginView.class));
        Button signUpButton = new Button("Sign Up", e -> UI.getCurrent().navigate(RegistrationView.class));
        Button logoutButton = new Button("Logout", e -> logout());

        Div div1 = new Div(), div2 = new Div(), div3 = new Div();
        leftLayout.add(appName);
        centerLayout.add(div1, homeButton, aboutButton, select, searchBox, searchType, searchButton, cartButton, div2);
        centerLayout.setFlexGrow(1, div1);
        centerLayout.setFlexGrow(1, div2);
        boolean isLoggedIn = marketController.isLoggedIn(sessionId).value;
        if (marketController.isLoggedIn(sessionId).value)
            rightLayout.add(div3, logoutButton);
        else
            rightLayout.add(div3, loginButton, signUpButton);
        rightLayout.setFlexGrow(1, div3);
        layout.add(leftLayout, centerLayout, rightLayout);
        layout.setFlexGrow(1, leftLayout);
        layout.setFlexGrow(1, centerLayout);
        layout.setFlexGrow(1, rightLayout);
        vl.add(layout);
        return vl;
    }

    private void logout() {
        sessionId = marketController.logout(sessionId).value;
        if (sessionId != null)
            UI.getCurrent().navigate(HelloWorldView.class);
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
        actionsMap.put("Add Store Manager", () -> UI.getCurrent().navigate(AddStoreManager.class));
        actionsMap.put("Add Store Owner", () -> UI.getCurrent().navigate(AddStoreOwner.class));
        actionsMap.put("Add Payment Method", () -> UI.getCurrent().navigate(AddPaymentMethod.class));
        actionsMap.put("Add Product", () -> UI.getCurrent().navigate(AddProduct.class));
        actionsMap.put("Remove Product", () -> UI.getCurrent().navigate(RemoveProductFromStore.class));
        actionsMap.put("Open a New Store", () -> UI.getCurrent().navigate(OpenStore.class));
        actionsMap.put("Add Discount", () -> UI.getCurrent().navigate(AddDiscount.class));
        actionsMap.put("Edit Product", () -> UI.getCurrent().navigate(EditProduct.class));
        actionsMap.put("Add Store Manager Permission", () -> UI.getCurrent().navigate(addStoreManagerPermissions.class));
        actionsMap.put("Remove Store Manager Permission", () -> UI.getCurrent().navigate(removeStoreManagerPermissions.class));
        actionsMap.put("Close Store", () -> UI.getCurrent().navigate(CloseStore.class));
        actionsMap.put("My Stores", () -> UI.getCurrent().navigate(ManagerStoresView.class));

        select.setItems(actionsMap.keySet().stream().sorted().collect(Collectors.toList()));
        select.addValueChangeListener(event -> actionsMap.get(event.getValue()).run());
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

    private void addStoreOwner() {
        UI.getCurrent().navigate(AddStoreOwner.class);
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    private AppNav createCategoryNev() {
        AppNav nav = new AppNav();

        List<String> catego = this.marketController.getAllCategories();
        for (String cat : catego) {
            nav.addItem(new AppNavItem(cat, CategoryView.class, LineAwesomeIcon.EMPIRE.create()));
        }
        return nav;
    }
}
