package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.PurchaseProductDTO;
import ServiceLayer.ResponseT;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "UserPurchases", layout = MainLayout.class)
@PreserveOnRefresh
public class UserPurchases extends VerticalLayout {
    @Autowired
    public UserPurchases() {
        MarketController marketController = MarketController.getInstance();
        Header header = new Header();
        header.setText("User purchases");

        Grid<PurchaseDTO> purchaseGrid = new Grid<>(PurchaseDTO.class, false);
        ResponseT<List<PurchaseDTO>> purchaseListResponse = marketController.getUserPurchaseHistory(MainLayout.getSessionId());
        purchaseGrid.setItems(purchaseListResponse.getError_occurred() ? new ArrayList<>() : purchaseListResponse.value);
        purchaseGrid.addColumn(PurchaseDTO::getPurchaseId).setHeader("#").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addColumn(PurchaseDTO::getPurchaseDateTime).setHeader("Purchase Date & Time").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addComponentColumn(purchaseDTO -> new Button("view details", e -> viewPurchaseDetailsDialog(purchaseDTO))).setFlexGrow(0).setAutoWidth(true);
        purchaseGrid.setWidthFull();

        add(purchaseGrid);
        setWidthFull();
    }

    private void viewPurchaseDetailsDialog(PurchaseDTO purchase) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Purchase #" + purchase.getPurchaseId());

        Grid<PurchaseProductDTO> purchaseGrid = new Grid<>(PurchaseProductDTO.class, false);
        purchaseGrid.setItems(purchase.getProductDTOList());
        purchaseGrid.addColumn(PurchaseProductDTO::getProductName).setHeader("Product name").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addColumn(PurchaseProductDTO::getPrice).setHeader("Price").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addColumn(PurchaseProductDTO::getQuantity).setHeader("Quantity").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.setWidthFull();

        VerticalLayout vl = new VerticalLayout(header, purchaseGrid);
        dialog.add(vl);
        dialog.setWidthFull();
        dialog.open();
    }
}
