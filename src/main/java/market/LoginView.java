package market;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "login", layout = MainUI.class)
public class LoginView extends VerticalLayout {

    public LoginView() {
        FormLayout formLayout = new FormLayout();
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            // Perform login logic here
            Notification.show("Login successful");
        });

        formLayout.add(usernameField, passwordField, loginButton);
        add(formLayout);
    }
}
