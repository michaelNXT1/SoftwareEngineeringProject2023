package application.views;

import BusinessLayer.Product;
import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.Response;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "SearchResultView", layout = MainLayout.class)
public class SearchResultView extends VerticalLayout {
    private TextField cardNumberField;
    private Select<String> monthSelect;
    private Select<String> yearSelect;
    private TextField cvvField;
    private TextField storeIdField;
    private Button submitButton;
    private MarketController marketController;
    private Header header;

    @Autowired
    public SearchResultView() {
        this.marketController = MarketController.getInstance();
        this.header = new Header();
        this.header.setText("Search results for " + marketController.getSearchKeyword(MainLayout.getSessionId()));
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
            quantity.setMin(0);
            Button addToCartButton = new Button("Add to Cart", e -> addToCart(product, quantity.getValue()));
            hl.add(quantity, addToCartButton);
            return hl;
        }).setHeader("Add to Cart");

        List<ProductDTO> people = marketController.getSearchResults(MainLayout.getSessionId());
        grid.setItems(people);
        grid.setVisible(people.isEmpty());
        add(grid);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private String getStoreName(ProductDTO p){
        return marketController.getStore(MainLayout.getSessionId(), p.getStoreId()).getStoreName();
    }

    private void addToCart(ProductDTO p, int quantity) {
        Response r = marketController.addProductToCart(MainLayout.getSessionId(), p.getStoreId(), p.getProductId(), quantity);
        if (r.getError_occurred())
            Notification.show(r.error_message);
        else
            Notification.show("Product added to cart successfully");
    }

    private void getProducts() {
        List<ProductDTO> products = marketController.getSearchResults(MainLayout.getSessionId());
    }

    private void addPaymentMethod() {
        String cardNumber = cardNumberField.getValue();
        String month = monthSelect.getValue();
        String year = yearSelect.getValue();
        String cvv = cvvField.getValue();
        Notification.show(Boolean.toString(marketController.addPaymentMethod(MainLayout.getSessionId(), cardNumber, month, year, cvv)));
    }
}
