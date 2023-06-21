package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.Response;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class RemoveMember extends Dialog {
    private final TextField usernameField;
    private final MarketController marketController;

    public RemoveMember() {
        VerticalLayout vl = new VerticalLayout();
        Header header = new Header();
        header.setText("Remove Member");
        this.usernameField = new TextField("username to remove");
        Button submitButton = new Button("remove", e -> removeMember());
        this.marketController = MarketController.getInstance();

        vl.add(header, usernameField, submitButton);
        vl.setSizeFull();
        vl.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        setWidth("50%");
        setHeight("50%");
        add(vl);
    }

    private void removeMember() {
        String username = usernameField.getValue();
        Response r = marketController.removeMember(MainLayout.getSessionId(), username);
        if (r.getError_occurred())
            Notification.show(r.error_message);
        Notification.show("great success you succeeded to remove" + username);
    }
}
