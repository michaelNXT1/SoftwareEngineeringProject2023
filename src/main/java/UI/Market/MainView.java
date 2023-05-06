package UI.Market;

import UI.Market.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "main")
public class MainView extends VerticalLayout {
    public MainView() {
        H1 header = new H1("Welcome to my website");
        Button loginButton = new Button("Login", e -> UI.getCurrent().navigate(LoginView.class));

        add(header, loginButton);
        setAlignItems(Alignment.CENTER);
    }
}
