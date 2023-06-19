package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.StoreDTO;
import ServiceLayer.ResponseT;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@PageTitle("GetPurchaseHistory- ")
@Route(value = "GetPurchaseHistory ")
public class GetPurchaseHistory extends VerticalLayout {
    private  Button submitButton;
    private  Header header;
    private  TextField storeIdField;
    private MarketController marketController;

        private Grid<PurchaseDTO> grid;

        @Autowired
        public GetPurchaseHistory() throws Exception {
            this.header = new Header();
            this.header.setText(" Get Purchase History for Store");
            this.storeIdField = new TextField("store Id");
            this.marketController = MarketController.getInstance();

            // Create grid for member data
            grid = new Grid<>(PurchaseDTO.class);
            grid.setColumns("purchaseId", "purchaseDateTime", "products"); // Customize the columns as per your PurchaseDTO
            this.submitButton = new Button("GetPurchaseHistory", e -> submit());
            add(header,storeIdField, submitButton);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            getStyle().set("text-align", "center");

            add(grid);
        }

        private void submit() {
            int storeId = Integer.parseInt(storeIdField.getValue());
            ResponseT<Map<StoreDTO, List<PurchaseDTO>>> r= marketController.getStoresPurchases(MainLayout.getSessionId());
            if (r.getError_occurred())
                Notification.show(r.error_message, 3000, Notification.Position.MIDDLE);
            else {
                Notification.show("you get Information About Members", 3000, Notification.Position.MIDDLE);
                List<PurchaseDTO> purchases =r.value.get(storeId);
                grid.setItems(purchases);
            }
        }
}
