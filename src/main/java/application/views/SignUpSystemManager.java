package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.Response;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "SignUpSystemManager", layout = MainLayout.class)
@PreserveOnRefresh
public class SignUpSystemManager extends VerticalLayout {
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Button submitButton;
    private final MarketController marketController;
    private final Header header;

    @Autowired
    public SignUpSystemManager() throws Exception {
        this.header = new Header();
        this.header.setText("SignUpSystemManager");
        this.usernameField = new TextField("Username");
        this.passwordField = new PasswordField("Password");
        this.submitButton = new Button("Registration", e -> registration());
        this.marketController = MarketController.getInstance();

        add(header, usernameField, passwordField, submitButton);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void registration() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();
        Response ans = marketController.signUpSystemManager(username, password);
        if (ans.getError_occurred())
            Notification.show(ans.error_message);
    }
}


