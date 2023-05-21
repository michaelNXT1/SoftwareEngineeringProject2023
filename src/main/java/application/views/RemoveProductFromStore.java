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
@Route(value = "RemoveProductFromStore",layout = MainLayout.class)
@PreserveOnRefresh
public class RemoveProductFromStore extends VerticalLayout {
        private TextField storeIdField;
        private TextField productIdField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public RemoveProductFromStore(){
            this.header = new Header();
            this.header.setText("Remove product from store");
            this.storeIdField = new TextField("store Id");
            this.productIdField = new TextField("product id");
            this.submitButton = new Button("remove", e -> AddProduct());
            this.marketController = MarketController.getInstance();

            add(header,storeIdField,productIdField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void AddProduct() {

            int storeId = Integer.parseInt(storeIdField.getValue());
            int productId = Integer.parseInt(productIdField.getValue());
            marketController.removeProductFromStore( MainLayout.getSessionId(),storeId,productId);
        }


    }



