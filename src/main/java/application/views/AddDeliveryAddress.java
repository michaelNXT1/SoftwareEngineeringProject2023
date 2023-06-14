package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.Response;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "addDeliveryAddress", layout = MainLayout.class)
@PreserveOnRefresh
public class AddDeliveryAddress extends VerticalLayout {
    private final MarketController marketController;
    private final TextField nameField;
    private final TextField addressField;
    private final TextField cityField;
    private final TextField countryField;
    private final TextField zipField;
    private final Header header;

    @Autowired
    public AddDeliveryAddress(){
        this.marketController = MarketController.getInstance();
        this.header = new Header();
        this.header.setText("Add Delivery Address");
        nameField=new TextField("Receiver Name");
        addressField=new TextField("Address");
        cityField=new TextField("City");
        countryField=new TextField("Country");
        zipField=new TextField("Zip Code");
        Button submitButton = new Button("add", e -> addDeliveryAddress());

        add(header, nameField, addressField, cityField, countryField, zipField, submitButton);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void addDeliveryAddress() {
        Response r=marketController.addSupplyDetails(MainLayout.getSessionId(),
                nameField.getValue(),
                addressField.getValue(),
                cityField.getValue(),
                countryField.getValue(),
                zipField.getValue());
        if (r.getError_occurred())
            Notification.show(r.error_message);
        else
            Notification.show("Delivery address added successfully");
    }
}
