package application.views.StoreManagement;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.DTOs.OfferDTO;
import ServiceLayer.Response;
import application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class OfferManagementView extends VerticalLayout {
    private final int storeId;
    private final Grid<OfferDTO> offerGrid;
    private final MarketController marketController;

    public OfferManagementView(MarketController marketController, int storeId) {
        this.storeId = storeId;
        this.marketController = marketController;
        HorizontalLayout hl = new HorizontalLayout();
        Div productsDiv = new Div();
        hl.add(new H1("Offer List"), productsDiv);
        hl.setFlexGrow(1, productsDiv);
        hl.setWidthFull();
        offerGrid = new Grid<>(OfferDTO.class, false);
        offerGrid.addColumn(offerDTO -> offerDTO.getProduct().getProductName()).setHeader("Product").setSortable(true).setTextAlign(ColumnTextAlign.START);
        offerGrid.addColumn(OfferDTO::getPricePerItem).setHeader("Price per item").setSortable(true).setTextAlign(ColumnTextAlign.START);
        offerGrid.addColumn(OfferDTO::getQuantity).setHeader("Quantity").setSortable(true).setTextAlign(ColumnTextAlign.START);
        offerGrid.addColumn(offerDTO -> offerDTO.getOfferingUser().getUsername()).setHeader("Offered by").setSortable(true).setTextAlign(ColumnTextAlign.START);
        offerGrid.addComponentColumn(offerDTO -> {
            String username = marketController.getUsername(MainLayout.getSessionId()).value;
            MemberDTO member = offerDTO.getApprovingMembers().keySet().stream().filter(memberDTO -> memberDTO.getUsername().equals(username)).findFirst().orElse(null);
            int offerResponse = offerDTO.getApprovingMembers().get(member);
            HorizontalLayout buttonHl = new HorizontalLayout();
            switch (offerResponse) {
                case -1 -> {
                    Button acceptButton = new Button("Accept", e -> acceptOffer(offerDTO));
                    Button rejectButton = new Button("Reject", e -> rejectOffer(offerDTO));
                    buttonHl.add(acceptButton, rejectButton);
                }
                case 0 -> buttonHl.add(new Label("You have rejected this offer"));
                case 1 -> buttonHl.add(new Label("You have accepted this offer"));
            }
            return buttonHl;
        });
        add(hl, offerGrid);
    }

    private void rejectOffer(OfferDTO offerDTO) {
        Response response = marketController.rejectOffer(MainLayout.getSessionId(), storeId, offerDTO.getOfferId());
        if (response.getError_occurred())
            Notification.show(response.error_message);
        else
            Notification.show("Offer rejected successfully");
    }

    private void acceptOffer(OfferDTO offerDTO) {
        Response response = marketController.acceptOffer(MainLayout.getSessionId(), storeId, offerDTO.getOfferId());
        if (response.getError_occurred())
            Notification.show(response.error_message);
        else
            Notification.show("Offer accepted successfully");
    }

    public void setOfferGrid(List<OfferDTO> offerDTOList) {
        offerGrid.setItems(offerDTOList);
    }

}
