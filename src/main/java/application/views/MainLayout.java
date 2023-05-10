package application.views;


import CommunicationLayer.MarketController;
import application.components.AppNav;
import application.components.AppNavItem;
import application.views.about.AboutView;
import application.views.category.CategoryView;
import application.views.helloworld.HelloWorldView;
import application.views.login.LoginView;
import application.views.registration.RegistrationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.List;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {
    private static String sessionId;

    MarketController marketController = MarketController.getInstance();

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
        addToNavbar(createHeaderContent());
        addDrawerContent();
        sessionId = null;
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
        Button homeButton = new Button("Home");
        homeButton.addClickListener(e -> UI.getCurrent().navigate(HelloWorldView.class));
        Button aboutButton = new Button("About");
        aboutButton.addClickListener(e -> UI.getCurrent().navigate(AboutView.class));
        Button optionsButton = new Button("Options");

        Select<String> select = new Select<>();
        select.setItems(
                "Open Store",
                "Add Payment Details"
        );
        select.setPlaceholder("Placeholder");
        select.addValueChangeListener(event -> {
            String selectedItem = event.getValue();
            Notification.show("Selected item: " + event.getValue());
            // Execute a lambda function when an item is clicked
            System.out.println("Selected item: " + selectedItem);
        });
        TextField searchBox = new TextField();
        searchBox.setPlaceholder("Search product");
        Button searchButton = new Button("", VaadinIcon.SEARCH.create());
        Button loginButton = new Button("Login");
        loginButton.addClickListener(e -> UI.getCurrent().navigate(LoginView.class));
        Button signUpButton = new Button("Sign Up");
        signUpButton.addClickListener(e -> UI.getCurrent().navigate(RegistrationView.class));

        Div div1 = new Div(), div2 = new Div(), div3 = new Div();
        leftLayout.add(appName);
        centerLayout.add(div1, homeButton, aboutButton, select, searchBox, searchButton, div2);
        centerLayout.setFlexGrow(1, div1);
        centerLayout.setFlexGrow(1, div2);
        rightLayout.add(div3, loginButton, signUpButton);
        rightLayout.setFlexGrow(1, div3);
        layout.add(leftLayout, centerLayout, rightLayout);
        layout.setFlexGrow(1, leftLayout);
        layout.setFlexGrow(1, centerLayout);
        layout.setFlexGrow(1, rightLayout);
        vl.add(layout);
        return vl;
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

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("Hello World", LineAwesomeIcon.GLOBE_SOLID.create(), HelloWorldView.class), //

                new MenuItemInfo("About", LineAwesomeIcon.FILE.create(), AboutView.class), //

        };
    }

}
