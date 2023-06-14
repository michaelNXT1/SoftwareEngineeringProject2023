package application.views;

import CommunicationLayer.MarketController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
    @Route(value = "removeMember",layout = MainLayout.class)
    @PreserveOnRefresh
    public class RemoveMember extends VerticalLayout{
        private TextField usernameField;
        private Button submitButton;
        private MarketController marketController;
        private Header header;

        @Autowired
        public RemoveMember(){
            this.header = new Header();
            this.header.setText("Remove Member");
            this.usernameField = new TextField("username to remove");
            this.submitButton = new Button("remove", e -> RemoveMember());
            this.marketController = MarketController.getInstance();

            add(header,usernameField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");
        }

        private void RemoveMember() {
            String username = usernameField.getValue();
            marketController.removeMember( MainLayout.getSessionId(),username);
        }
}
