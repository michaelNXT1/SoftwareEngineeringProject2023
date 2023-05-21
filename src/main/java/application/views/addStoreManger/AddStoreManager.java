package application.views.addStoreManger;

import CommunicationLayer.MarketController;
import CommunicationLayer.NotificationController;
import application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

@Route(value = "setPositionOfMemberToStoreManager",layout = MainLayout.class)
@PreserveOnRefresh
public class AddStoreManager extends VerticalLayout{
        private TextField usernameField;
        private TextField storeIdField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public AddStoreManager(){
            this.header = new Header();
            this.header.setText("Add Store Manager");
            this.usernameField = new TextField("Username");
            this.storeIdField = new TextField("storeId");
            this.submitButton = new Button("add", e -> setPositionOfMemberToStoreManager());
            this.marketController = MarketController.getInstance();

            add(header,usernameField,storeIdField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void setPositionOfMemberToStoreManager() {
            String username = usernameField.getValue();
            int storeId = Integer.parseInt(storeIdField.getValue());
            marketController.setPositionOfMemberToStoreManager( MainLayout.getSessionId(),storeId,username);
        }
}
