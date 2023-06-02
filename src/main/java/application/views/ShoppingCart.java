package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.ShoppingCartDTO;
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

import java.util.Map;

@Route(value = "ShoppingCart", layout = MainLayout.class)
@PreserveOnRefresh
public class
ShoppingCart extends VerticalLayout {
    private Header myBag;
    private Header total;
    private Grid<ProductDTO> productGrid;
    ShoppingCartDTO shoppingCart;
    Map<ProductDTO, Integer> productDTOList;
    private MarketController marketController;


    @Autowired
    public ShoppingCart() {
        this.marketController = MarketController.getInstance();
        shoppingCart = marketController.getShoppingCart(MainLayout.getSessionId()).value;
        productDTOList = shoppingCart.getProducts();
        productGrid = new Grid<>(ProductDTO.class, false);
        productGrid.addColumn(ProductDTO::getProductName).setHeader("Product Name");
        productGrid.addColumn(this::getStoreName).setHeader("Store Name");
        productGrid.addColumn(productDTO -> String.format("%.2f", productDTO.getPrice())).setHeader("Price per item");
        productGrid.addComponentColumn(product -> {
            HorizontalLayout hl = new HorizontalLayout();
            IntegerField quantity = new IntegerField();
            quantity.setValue(productDTOList.get(product));
            quantity.setStepButtonsVisible(true);
            quantity.setMin(0);
            Button updateButton = new Button("Update", e -> updateQuantity(product, quantity.getValue()));
            Button removeButton = new Button("Remove", e -> removeProduct(product));
            hl.add(quantity, updateButton, removeButton);
            return hl;
        }).setHeader("Quantity");
        productGrid.addColumn(p -> p.getPrice() * productDTOList.get(p)).setHeader("Total").setFooter("Total: " + String.format("%.2f", calculateTotalPrice()));

        productGrid.setItems(productDTOList.keySet());

        Button purchaseButton = new Button("Purchase cart");
        setHorizontalComponentAlignment(Alignment.END, purchaseButton);
        add(productGrid, purchaseButton);
        setWidthFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private double calculateTotalPrice() {
        double ret = 0;
        for (Map.Entry<ProductDTO, Integer> e : productDTOList.entrySet())
            ret += e.getKey().getPrice() * e.getValue();
        return ret;
    }

    private void updateQuantity(ProductDTO product, Integer value) {
        Response r = marketController.changeProductQuantity(MainLayout.getSessionId(), product.getStoreId(), product.getProductId(), value);
        if (r.getError_occurred())
            Notification.show(r.error_message);
        else {
            Notification.show("quantity updated successfully");
            productDTOList = marketController.getShoppingCart(MainLayout.getSessionId()).value.getProducts();
            refreshGrid();
        }
    }

    private void removeProduct(ProductDTO product) {
        productDTOList.remove(product);
        Response r = marketController.removeProductFromCart(MainLayout.getSessionId(), product.getStoreId(), product.getProductId());
        if (r.getError_occurred())
            Notification.show(r.error_message);
        else {
            Notification.show("product removed successfully");
            productDTOList = marketController.getShoppingCart(MainLayout.getSessionId()).value.getProducts();
            refreshGrid();
        }
    }

    private String getStoreName(ProductDTO p) {
        return marketController.getStore(MainLayout.getSessionId(), p.getStoreId()).value.getStoreName();
    }

    private void refreshGrid() {
        if (productDTOList.size() > 0) {
            productGrid.setVisible(true);
//            productGrid.getDataProvider().refreshAll();
            productGrid.setItems(productDTOList.keySet());
        } else {
            productGrid.setVisible(false);
        }
    }

}
