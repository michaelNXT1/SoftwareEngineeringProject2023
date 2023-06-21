package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.PurchaseProductDTO;
import ServiceLayer.DTOs.StoreDTO;
import ServiceLayer.ResponseT;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@PageTitle("GetPurchaseHistory ")
@Route(value = "GetPurchaseHistory ")
public class GetPurchaseHistory extends Dialog {
    private  Header header;
    private MarketController marketController;

        private Grid<PurchaseDTO> grid;

        @Autowired
        public GetPurchaseHistory() {
            VerticalLayout vl=new VerticalLayout();
            this.header = new Header();
            this.header.setText(" Get Purchase History for Store");
            this.marketController = MarketController.getInstance();

            // Create grid for member data
            grid = new Grid<>(PurchaseDTO.class);
            grid.setColumns("purchaseId", "purchaseDateTime", "productDTOList"); // Customize the columns as per your PurchaseDTO


            Grid<PurchaseDTO> purchaseGrid = new Grid<>(PurchaseDTO.class, false);
            ResponseT<Map<StoreDTO, List<PurchaseDTO>>> purchaseListResponse = marketController.getStoresPurchases(MainLayout.getSessionId());

            List<PurchaseDTO> mergedList = purchaseListResponse.getError_occurred()?new ArrayList<>():purchaseListResponse.value.values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            purchaseGrid.setItems(mergedList);
            purchaseGrid.addColumn(PurchaseDTO::getPurchaseId).setHeader("#").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
            purchaseGrid.addColumn(PurchaseDTO::getUsername).setHeader("Username").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
            purchaseGrid.addColumn(PurchaseDTO::getPurchaseDateTime).setHeader("Purchase Date & Time").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
            purchaseGrid.addComponentColumn(purchaseDTO -> new Button("view details", e -> viewPurchaseDetailsDialog(purchaseDTO))).setFlexGrow(0).setAutoWidth(true);
            purchaseGrid.setWidthFull();

            vl.add(header, purchaseGrid);
            vl.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            vl.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
            vl.getStyle().set("text-align", "center");
            setWidth("50%");
            setHeight("50%");
            add(vl);
        }

    private void viewPurchaseDetailsDialog(PurchaseDTO purchase) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Purchase #" + purchase.getPurchaseId());

        Grid<PurchaseProductDTO> purchaseGrid = new Grid<>(PurchaseProductDTO.class, false);
        purchaseGrid.setItems(purchase.getProductDTOList());
        purchaseGrid.addColumn(PurchaseProductDTO::getProductName).setHeader("Product name").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addColumn(purchaseProductDTO -> purchaseProductDTO.getPrice() + "§").setHeader("Price").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addColumn(PurchaseProductDTO::getQuantity).setHeader("Quantity").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.setWidthFull();

        VerticalLayout vl = new VerticalLayout(header, purchaseGrid);
        dialog.add(vl);
        dialog.setWidthFull();
        dialog.open();
    }
}
