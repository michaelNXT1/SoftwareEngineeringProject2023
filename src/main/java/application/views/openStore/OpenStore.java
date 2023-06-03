package application.views.openStore;

import CommunicationLayer.MarketController;
import CommunicationLayer.NotificationController;
import application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
@Route(value = "openStore",layout = MainLayout.class)
@PreserveOnRefresh
public class OpenStore  extends VerticalLayout {
        private TextField storeNameField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public OpenStore() throws Exception {
            this.header = new Header();
            this.header.setText("Open Store");
            this.storeNameField = new TextField("store name");
            this.submitButton = new Button("open", e -> openStore());
            this.marketController = MarketController.getInstance();

            add(header,storeNameField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void openStore() {
            String storeName = storeNameField.getValue();
            marketController.openStore( MainLayout.getSessionId(),storeName);
        }

}
