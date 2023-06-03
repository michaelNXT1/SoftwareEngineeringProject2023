package application.views.login;

import CommunicationLayer.MarketController;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

@Route(value = "Login",layout = MainLayout.class)
@PreserveOnRefresh
public class LoginView extends VerticalLayout{
    private TextField usernameField;
    private PasswordField passwordField;
    private Button submitButton;
    private MarketController marketController;
    private Header header;


    @Autowired
    public LoginView() throws Exception {
        this.header = new Header();
        this.header.setText("Login");
        this.usernameField = new TextField("Username");
        this.passwordField = new PasswordField("Password");
        this.submitButton = new Button("Login", e -> login());
        this.marketController = MarketController.getInstance();

        add(header,usernameField, passwordField, submitButton);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void login() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();
        Response r = marketController.login(username,password);
        MainLayout.setSessionId(((ResponseT<String>) r).value);
        if (r.getError_occurred())
            Notification.show(r.error_message, 3000, Notification.Position.MIDDLE);
        else
            Notification.show("you logged in successfully", 3000, Notification.Position.MIDDLE);

    }
}
