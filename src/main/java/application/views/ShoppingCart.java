package application.views;

import CommunicationLayer.MarketController;
import CommunicationLayer.NotificationController;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.PurchaseDTO;
import ServiceLayer.DTOs.ShoppingCartDTO;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
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
    private final Span cartEmptySpan;
    private final Span totalSpan;
    private final Grid<ProductDTO> productGrid;
    private final MarketController marketController;
    ShoppingCartDTO shoppingCart;
    Map<ProductDTO, Integer> productDTOList;


    @Autowired
    public ShoppingCart() {
        this.marketController = MarketController.getInstance();
        shoppingCart = marketController.getShoppingCart(MainLayout.getSessionId()).value;
        productDTOList = shoppingCart.getProducts();
        productGrid = new Grid<>(ProductDTO.class, false);
        productGrid.addColumn(ProductDTO::getProductName).setHeader("Product Name");
        productGrid.addColumn(this::getStoreName).setHeader("Store Name");
        productGrid.addComponentColumn(this::initPriceColumn).setHeader("Price per item");
        productGrid.addComponentColumn(this::initQuantityColumn).setHeader("Quantity");
        totalSpan = new Span("Total: " + String.format("%.2f", calculateTotalPrice()));
        Button purchaseButton = new Button("Purchase cart", e -> purchaseShoppingCart());
        Span paymentDetails = new Span("Please add payment details.");
        VerticalLayout vl = new VerticalLayout();
        vl.add(totalSpan, purchaseButton, paymentDetails);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        productGrid.addColumn(this::initTotalColumn).setHeader("Total").setFooter(vl).setKey("price");
        ResponseT<Boolean> paymentDetailsExist = marketController.hasPaymentMethod(MainLayout.getSessionId());
        if (paymentDetailsExist.getError_occurred()) {
            purchaseButton.setEnabled(false);
            paymentDetails.setText(paymentDetailsExist.error_message);
        } else {
            purchaseButton.setEnabled(paymentDetailsExist.value);
            paymentDetails.setVisible(!paymentDetailsExist.value);
        }
        productGrid.setItems(productDTOList.keySet());
        cartEmptySpan = new Span("Your cart is empty");
        cartEmptySpan.setVisible(false);
        add(productGrid, cartEmptySpan);
        setWidthFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void purchaseShoppingCart() {
        ResponseT<PurchaseDTO> r = marketController.purchaseShoppingCart(MainLayout.getSessionId());
        if (r.getError_occurred())
            Notification.show(r.error_message);
        else {
            Notification.show("SUCCESS");
            refreshGrid();
        }
    }

    private double calculateTotalPrice() {
        double ret = 0;
        for (Map.Entry<ProductDTO, Integer> e : productDTOList.entrySet()) {
            ResponseT<Double> productDiscount = marketController.getProductDiscountPercentageInCart(MainLayout.getSessionId(), e.getKey().getStoreId(), e.getKey().getProductId());
            if (productDiscount.getError_occurred())
                ret += e.getKey().getPrice() * e.getValue();
            else
                ret += e.getKey().getPrice() * (1.0 - productDiscount.value) * e.getValue();
        }
        return ret;
    }

    private void updateQuantity(ProductDTO product, Integer value) {
        Response r = marketController.changeProductQuantity(MainLayout.getSessionId(), product.getStoreId(), product.getProductId(), value);
        if (r.getError_occurred())
            Notification.show(r.error_message);
        else {
            Notification.show("quantity updated successfully");
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
            refreshGrid();
        }
    }

    private String getStoreName(ProductDTO p) {
        return marketController.getStore(MainLayout.getSessionId(), p.getStoreId()).value.getStoreName();
    }

    private void refreshGrid() {
        productDTOList = marketController.getShoppingCart(MainLayout.getSessionId()).value.getProducts();
        if (productDTOList.size() > 0) {
            productGrid.setVisible(true);
            productGrid.setItems(productDTOList.keySet());
            totalSpan.setText("Total: " + String.format("%.2f", calculateTotalPrice()));
        } else {
            cartEmptySpan.setVisible(true);
            productGrid.setVisible(false);
        }
    }

    private HorizontalLayout initQuantityColumn(ProductDTO product) {
        HorizontalLayout hl = new HorizontalLayout();
        IntegerField quantity = new IntegerField();
        quantity.setValue(productDTOList.get(product));
        quantity.setStepButtonsVisible(true);
        quantity.setMin(0);
        Button updateButton = new Button("Update", e -> updateQuantity(product, quantity.getValue()));
        Button removeButton = new Button("Remove", e -> removeProduct(product));
        hl.add(quantity, updateButton, removeButton);
        return hl;
    }

    private Object initTotalColumn(ProductDTO p) {
        ResponseT<Double> productDiscount = marketController.getProductDiscountPercentageInCart(MainLayout.getSessionId(), p.getStoreId(), p.getProductId());
        if (productDiscount.getError_occurred())
            return p.getPrice() * productDTOList.get(p);
        return String.format("%.2f", p.getPrice() * (1.0 - productDiscount.value) * productDTOList.get(p));
    }

    private Component initPriceColumn(ProductDTO productDTO) {
        ResponseT<Double> productDiscount = marketController.getProductDiscountPercentageInCart(MainLayout.getSessionId(), productDTO.getStoreId(), productDTO.getProductId());
        if (productDiscount.getError_occurred())
            return new Span(String.format("%.2f", productDTO.getPrice()));
        double discountPercentage = productDiscount.value;
        if (discountPercentage == 0.0)
            return new Span(String.format("%.2f", productDTO.getPrice()));
        return new Html("<span><s>" + String.format("%.2f", productDTO.getPrice()) + "</s> " + String.format("%.2f", productDTO.getPrice() * (1.0 - discountPercentage)) + "</span>");
    }
}
