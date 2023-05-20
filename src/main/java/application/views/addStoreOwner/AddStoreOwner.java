package application.views.addStoreOwner;

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

@Route(value = "setPositionOfMemberToStoreOwner",layout = MainLayout.class)
@PreserveOnRefresh
public class AddStoreOwner extends VerticalLayout {
    private TextField usernameField;
    private TextField storeIdField;
    private Button submitButton;
    private MarketController marketController;
    private Header header;

    @Autowired
    public AddStoreOwner(){
        this.header = new Header();
        this.header.setText("Add Store Owner");
        this.usernameField = new TextField("Username");
        this.storeIdField = new TextField("storeId");
        this.submitButton = new Button("add", e -> setPositionOfMemberToStoreOwner());
        this.marketController = MarketController.getInstance(new NotificationController());

        add(header,usernameField,storeIdField, submitButton);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void setPositionOfMemberToStoreOwner() {
        String username = usernameField.getValue();
        int storeId = Integer.parseInt(storeIdField.getValue());
        marketController.setPositionOfMemberToStoreOwner( MainLayout.getSessionId(),storeId,username);
    }


}
