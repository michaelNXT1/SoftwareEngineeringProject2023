package application.views;

import BusinessLayer.SystemManager;
import CommunicationLayer.MarketController;
import Repositories.IMapStringSystemManagerRepository;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
//@Route(value = "SystemManagerPermissions",layout = MainLayout.class)
//@PreserveOnRefresh
public class SystemManagerPermissions extends Dialog {
        private TextField usernameField;
        private PasswordField passwordField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

//        @Autowired
        public SystemManagerPermissions() {
            VerticalLayout vl=new VerticalLayout();
            this.header = new Header();
            this.header.setText("has permission");
            this.usernameField = new TextField("Username");
            this.passwordField = new PasswordField("Password");
            this.submitButton = new Button("Login", e -> login());
            this.marketController = MarketController.getInstance();

            vl.add(header,usernameField, passwordField, submitButton);
            vl.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            vl.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
            vl.getStyle().set("text-align", "center");
            add(vl);
        }

        private void login() {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            ResponseT<String> r = marketController.loginSystemManager(username,password);
            if (r.getError_occurred())
                Notification.show(r.error_message, 3000, Notification.Position.MIDDLE);
            else {
                Notification.show("you logged in successfully", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().navigate(SystemManagerOperations.class);
                MainLayout.setSessionId(r.value);
                close();
            }

        }
}
