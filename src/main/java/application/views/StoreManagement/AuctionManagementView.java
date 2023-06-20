package application.views.StoreManagement;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.BidDTO;
import ServiceLayer.DTOs.ProductDTO;
import application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class AuctionManagementView extends VerticalLayout {
    private final int storeId;
    private final Grid<ProductDTO> productGrid;
    private final MarketController marketController;

    public AuctionManagementView(MarketController marketController, int storeId) {
        this.storeId = storeId;
        this.marketController = marketController;
        HorizontalLayout productsHL = new HorizontalLayout();
        productsHL.add(new H1("Auctions List"));
        productsHL.setWidthFull();
        productGrid = new Grid<>(ProductDTO.class, false);
        productGrid.addColumn(ProductDTO::getProductName).setHeader("Name").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(productDTO -> productDTO.getPrice() + "§").setHeader("Starting Price").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(ProductDTO::getAmount).setHeader("Quantity").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(productDTO -> productDTO.getAuctionEndTime().isBefore(LocalDateTime.now()) ? "ended" : productDTO.getAuctionEndTime()).setHeader("End time");
        productGrid.addColumn(productDTO -> {
            BidDTO highestBidder = productDTO.getBidders().stream().max(Comparator.comparingDouble(BidDTO::getOfferedPrice)).orElse(null);
            if (highestBidder == null)
                return "None yet";
            return highestBidder.getOfferingUser().getUsername();
        }).setHeader("Highest Bidder");
        productGrid.addComponentColumn(productDTO -> {
            if (productDTO.getAuctionEndTime().isBefore(LocalDateTime.now()))
                return new Button("Confirm purchase", e -> confirmPurchase(productDTO));
            return new Div();
        });
        add(productsHL, productGrid);
    }

    private void confirmPurchase(ProductDTO productDTO) {
        marketController.confirmAuction(MainLayout.getSessionId(), storeId, productDTO.getProductId());
    }

    public void setProductGrid(List<ProductDTO> productDTOList) {
        productGrid.setItems(productDTOList);
    }
}
