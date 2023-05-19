package application.views;

import CommunicationLayer.MarketController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
@Route(value = "removeStoreManagerPermissions",layout = MainLayout.class)
@PreserveOnRefresh
public class removeStoreManagerPermissions extends VerticalLayout {
        private TextField usernameField;
        private TextField storeIdField;
        private TextField newPermissionField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public removeStoreManagerPermissions(){
            this.header = new Header();
            this.header.setText("remove permission store manger");
            this.usernameField = new TextField("storeManager");
            this.storeIdField = new TextField("storeId");
            this.newPermissionField = new TextField("permission to remove");
            this.submitButton = new Button("remove", e -> removeStoreManagerPermissions());
            this.marketController = MarketController.getInstance();

            add(header,usernameField,storeIdField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void removeStoreManagerPermissions() {
            String username = usernameField.getValue();
            int storeId = Integer.parseInt(storeIdField.getValue());
            int newPermission = Integer.parseInt(storeIdField.getValue());
            marketController.removeStoreManagerPermissions(MainLayout.getSessionId(),username,storeId,newPermission);
        }
    }


