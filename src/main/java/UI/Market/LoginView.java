package UI.Market;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.stereotype.Component;

@Component(value = "login")
@Route(value = "login")
@PWA(name = "Market App", shortName = "Market App")
public class LoginView extends FormLayout implements AppShellConfigurator {

    public LoginView() {
        TextField usernameField = new TextField("123");
        PasswordField passwordField = new PasswordField("123");
        Button loginButton = new Button("Login", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            // Perform login logic here
            Notification.show("Login successful");
        });

        add(usernameField, passwordField, loginButton);
    }

    @Override
    public void configurePage(AppShellSettings configuration) {
        configuration.addMetaTag("viewport", "width=device-width, initial-scale=1");
        configuration.addFavIcon("icon", "icons/icon.png", "96x96");
    }
}
