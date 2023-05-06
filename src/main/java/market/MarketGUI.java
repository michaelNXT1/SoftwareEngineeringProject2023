package market;

import CommunicationLayer.MarketController;
import ServiceLayer.Response;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@PWA(name = "Market GUI", shortName = "Market GUI")
@Theme(value = "Lumo", variant = Lumo.LIGHT)
public class MarketGUI extends VerticalLayout {

    private MarketController marketController;

    @Autowired
    public MarketGUI(MarketController marketController) {
        this.marketController = marketController;

        // Create GUI components
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login");
        loginButton.addClickListener(e -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            Response response = marketController.login(username, password);
            Notification.show(response.error_message);
        });

        // Create a form layout and add the components
        FormLayout formLayout = new FormLayout();
        formLayout.add(usernameField, passwordField, loginButton);

        // Add the form layout to the main layout
        add(formLayout);
    }
}