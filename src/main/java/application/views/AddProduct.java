package application.views;

import CommunicationLayer.MarketController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
@Route(value = "AddProduct",layout = MainLayout.class)
public class AddProduct extends VerticalLayout {
        private TextField storeIdField;
        private TextField productNameField;
        private TextField priceField;
        private TextField categoryField;
        private TextField quantityField;
        private TextField descriptionField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public AddProduct(){
            this.header = new Header();
            this.header.setText("Add product to store");
            this.storeIdField = new TextField("store Id");
            this.productNameField = new TextField("product name");
            this.priceField = new TextField("price");
            this.categoryField = new TextField("category");
            this.quantityField = new TextField("quantity");
            this.descriptionField = new TextField("description");
            this.submitButton = new Button("add", e -> AddProduct());
            this.marketController = MarketController.getInstance();

            add(header,storeIdField,productNameField,priceField,categoryField,quantityField,descriptionField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void AddProduct() {
            String category = categoryField.getValue();
            String productName = productNameField.getValue();
            String description = descriptionField.getValue();
            int storeId = Integer.parseInt(storeIdField.getValue());
            int price = Integer.parseInt(priceField.getValue());
            int quantity = Integer.parseInt(quantityField.getValue());
            marketController.addProduct( MainLayout.getSessionId(),storeId,productName,price,category,quantity,description);
        }


    }

