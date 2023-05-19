package application.views;

import CommunicationLayer.MarketController;
import application.views.addStoreManger.AddStoreManager;
import application.views.addStoreOwner.AddStoreOwner;
import application.views.openStore.OpenStore;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Route(value = "EditProduct",layout = MainLayout.class)
@PreserveOnRefresh
public class EditProduct extends VerticalLayout {
    private TextField editField;
    private TextField storeIdField;
    private TextField productIdField;
    private Button submitButton;
    private MarketController marketController;
    private Header header;

    @Autowired
    public EditProduct() {
        this.header = new Header();
        this.header.setText("Edit Product");
        this.storeIdField = new TextField("store id");
        this.productIdField = new TextField("product id");
        this.editField = new TextField("edit property");
        this.submitButton = new Button("edit");

        this.marketController = MarketController.getInstance();



        Select<String> select = new Select<>();
        Map<String, Runnable> actionsMap = new HashMap<>();
        actionsMap.put("edit product name", () -> submitButton.addClickListener(e-> editProductName()));
        actionsMap.put("edit product price", () -> submitButton.addClickListener(e-> editProductPrice()));
        actionsMap.put("edit product category",()-> submitButton.addClickListener(e-> editProductCategory()));

        add(header,select, storeIdField, productIdField,editField, submitButton);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

        select.setItems(actionsMap.keySet());
        select.addValueChangeListener(event -> actionsMap.get(event.getValue()).run());
        select.setPlaceholder("Actions");

    }
    private void editProductName() {
        int storeId = Integer.parseInt(storeIdField.getValue());
        int productId = Integer.parseInt(productIdField.getValue());
        String newProductName = editField.getValue();
        marketController.editProductName( MainLayout.getSessionId(),storeId,productId,newProductName);
    }
    private void editProductPrice() {
        int storeId = Integer.parseInt(storeIdField.getValue());
        int productId = Integer.parseInt(productIdField.getValue());
        int newProductPrice = Integer.parseInt(editField.getValue());
        marketController.editProductPrice( MainLayout.getSessionId(),storeId,productId,newProductPrice);
    }
    private void editProductCategory() {
        int storeId = Integer.parseInt(storeIdField.getValue());
        int productId = Integer.parseInt(productIdField.getValue());
        String newProductCategory = editField.getValue();
        marketController.editProductCategory( MainLayout.getSessionId(),storeId,productId,newProductCategory);
    }


}
