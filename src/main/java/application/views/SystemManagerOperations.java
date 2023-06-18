package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
@Route(value = "SystemManagerOperations")
@PreserveOnRefresh
public class SystemManagerOperations extends VerticalLayout {
    private Button removeMember;
    private Button informationAboutMembers;
    private Button getpurchaseshistory;
    private MarketController marketController;
    private Header header;

    @Autowired
    public SystemManagerOperations() throws Exception {
        this.header = new Header();
        this.header.setText("System Manager Operation");
        this.removeMember = new Button("removeMember", e -> removeMember());
        this.informationAboutMembers = new Button("informationAboutMembers", e -> informationAboutMembers());
        this.marketController = MarketController.getInstance();
        this.getpurchaseshistory = new Button("getpurchaseshistory", e -> getpurchaseshistory());

        add(header, removeMember, informationAboutMembers, getpurchaseshistory);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void removeMember() {
        UI.getCurrent().navigate(RemoveMember.class);


    }

    private void informationAboutMembers() {
        UI.getCurrent().navigate(GetInformationAboutMembers.class);

    }

    private void getpurchaseshistory() {
        UI.getCurrent().navigate(GetPurchaseHistory.class);

    }
}

