package application.views.addStoreManger;

import CommunicationLayer.MarketController;
import application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
@Route(value = "AddStoreManager",layout = MainLayout.class)
public class AddStoreManager extends VerticalLayout{
        private TextField usernameField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public AddStoreManager(){
            this.header = new Header();
            this.header.setText("Add Store Manager");
            this.usernameField = new TextField("Username");
            this.submitButton = new Button("add", e -> login());
            this.marketController = MarketController.getInstance();

            add(header,usernameField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void login() {
            String username = usernameField.getValue();
          //  marketController.addStoreManagerPermissions(username,);
        }


}
