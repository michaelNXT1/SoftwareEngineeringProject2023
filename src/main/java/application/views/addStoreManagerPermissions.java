package application.views;

import CommunicationLayer.MarketController;
import CommunicationLayer.NotificationController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "addStoreManagerPermissions",layout = MainLayout.class)
@PreserveOnRefresh
public class addStoreManagerPermissions extends VerticalLayout {
        private TextField usernameField;
        private TextField storeIdField;

        private TextField newPermissionField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public addStoreManagerPermissions(){
            this.header = new Header();
            this.header.setText("Add permission to store manger");
            this.usernameField = new TextField("storeManager");
            this.storeIdField = new TextField("storeId");
            this.newPermissionField = new TextField("new permission");
            this.submitButton = new Button("add", e -> addStoreManagerPermissions());
            this.marketController = MarketController.getInstance();

            add(header,usernameField,storeIdField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void addStoreManagerPermissions() {
            String username = usernameField.getValue();
            int storeId = Integer.parseInt(storeIdField.getValue());
            int newPermission = Integer.parseInt(storeIdField.getValue());
            marketController.addStoreManagerPermissions(MainLayout.getSessionId(),username,storeId,newPermission);
        }
}
