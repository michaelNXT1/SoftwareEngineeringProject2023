package application.views;

import CommunicationLayer.MarketController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "SignUpSystemManager",layout = MainLayout.class)
@PreserveOnRefresh
public class SignUpSystemManager extends VerticalLayout {
        private TextField usernameField;
        private PasswordField passwordField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public SignUpSystemManager(){
            this.header = new Header();
            this.header.setText("SignUpSystemManager");
            this.usernameField = new TextField("Username");
            this.passwordField = new PasswordField("Password");
            this.submitButton = new Button("Registration", e -> regisration());
            this.marketController = MarketController.getInstance();

            add(header,usernameField, passwordField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void regisration() {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            Boolean ans = marketController.signUpSystemManager(username,password);
            Notification.show(ans.toString());
        }
    }

