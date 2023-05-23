package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
@Route(value = "closeStore",layout = MainLayout.class)
@PreserveOnRefresh
public class CloseStore extends VerticalLayout {
        private TextField storeIdField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public CloseStore(){
            this.header = new Header();
            this.header.setText("Close Store");
            this.storeIdField = new TextField("store Id");
            this.submitButton = new Button("close", e -> CloseStore());
            this.marketController = MarketController.getInstance();

            add(header,storeIdField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void CloseStore() {
            int storeId = Integer.parseInt(storeIdField.getValue());
            Response r = marketController.closeStore(MainLayout.getSessionId(),storeId);
            if (r.getError_occurred())
                Notification.show(r.error_message, 3000, Notification.Position.MIDDLE);
            else
                Notification.show("you logged in successfully", 3000, Notification.Position.MIDDLE);

        }

    }
