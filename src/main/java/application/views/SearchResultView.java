package application.views;

import CommunicationLayer.MarketController;
import CommunicationLayer.NotificationController;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.Response;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "SearchResultView", layout = MainLayout.class)
@PreserveOnRefresh
public class SearchResultView extends VerticalLayout {
    private final MarketController marketController;
    private final Header header;

    @Autowired
    public SearchResultView() {
        this.marketController = MarketController.getInstance();
        this.header = new Header();
        this.header.setText("Search results for " + marketController.getSearchKeyword(MainLayout.getSessionId()).value);
        add(header);
        Grid<ProductDTO> grid = new Grid<>(ProductDTO.class, false);
        grid.addColumn(ProductDTO::getProductName).setHeader("Product Name");
        grid.addColumn(this::getStoreName).setHeader("Store Name");
        grid.addColumn(ProductDTO::getPrice).setHeader("Price");
        grid.addComponentColumn(product -> {
            HorizontalLayout hl = new HorizontalLayout();
            IntegerField quantity = new IntegerField();
            quantity.setValue(1);
            quantity.setStepButtonsVisible(true);
            quantity.setMin(1);
            Button addToCartButton = new Button("Add to Cart", e -> addToCart(product, quantity.getValue()));
            hl.add(quantity, addToCartButton);
            return hl;
        }).setHeader("Add to Cart");

        List<ProductDTO> people = marketController.getSearchResults(MainLayout.getSessionId()).value;
        grid.setItems(people);
        grid.setVisible(!people.isEmpty());
        add(grid);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private String getStoreName(ProductDTO p){
        return marketController.getStore(MainLayout.getSessionId(), p.getStoreId()).value.getStoreName();
    }

    private void addToCart(ProductDTO p, int quantity) {
        Response r = marketController.addProductToCart(MainLayout.getSessionId(), p.getStoreId(), p.getProductId(), quantity);
        if (r.getError_occurred())
            Notification.show(r.error_message);
        else
            Notification.show("Product added to cart successfully");
    }
}
