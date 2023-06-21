package application.views;

import CommunicationLayer.MarketController;
import application.views.about.AboutView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "SystemManagerOperations")
@PreserveOnRefresh
public class SystemManagerOperations extends VerticalLayout {
    private final MarketController marketController;

    @Autowired
    public SystemManagerOperations() {
        Header header = new Header();
        header.setText("System Manager Operation");
        Button removeMember = new Button("Remove Member", e -> new RemoveMember().open());
        Button informationAboutMembers = new Button("Information AboutMembers", e -> new GetInformationAboutMembers().open());
        this.marketController = MarketController.getInstance();
        Button getpurchaseshistory = new Button("Get Purchase History", e -> new GetPurchaseHistory().open());
        Button logoutButton = new Button("logout", e -> {
            marketController.logout(MainLayout.getSessionId());
            UI.getCurrent().navigate(AboutView.class);
        });

        add(header, removeMember, informationAboutMembers, getpurchaseshistory, logoutButton);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}

