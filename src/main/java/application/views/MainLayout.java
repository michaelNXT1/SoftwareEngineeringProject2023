package application.views;


import CommunicationLayer.MarketController;
import application.components.AppNav;
import application.components.AppNavItem;
import application.views.category.CategoryView;
import application.views.about.AboutView;
import application.views.helloworld.HelloWorldView;
import application.views.login.LoginView;
import application.views.registration.RegistrationView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Whitespace;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.List;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {


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
        marketController.enterMarket();
    }

    private void addDrawerContent() {
        H1 appName = new H1("Categories");
        appName.addClassNames(FontSize.SMALL, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createCategoryNev());

        addToDrawer(header, scroller, createFooter());
    }


    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        Div layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);

        H1 appName = new H1("Eb2");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);
        layout.add(appName);

        Nav nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Top.MEDIUM, Padding.Vertical.XSMALL,Margin.Vertical.MEDIUM);

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            list.add(menuItem);

        }

        header.add(layout, nav);
        return header;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    private AppNav createCategoryNev(){
        AppNav nav = new AppNav();

        List<String> catego = this.marketController.getAllCategories();
        for(String cat: catego){
            nav.addItem(new AppNavItem(cat, CategoryView.class, LineAwesomeIcon.EMPIRE.create()));
        }
        return nav;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("Hello World", LineAwesomeIcon.GLOBE_SOLID.create(), HelloWorldView.class), //
                new MenuItemInfo("Sign Up", LineAwesomeIcon.CODEPEN.create(), RegistrationView.class), //
                new MenuItemInfo("Log In", LineAwesomeIcon.CAPSULES_SOLID.create(), LoginView.class), //
                new MenuItemInfo("About", LineAwesomeIcon.FILE.create(), AboutView.class), //

        };
    }

}
