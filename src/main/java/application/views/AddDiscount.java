package application.views;

import CommunicationLayer.MarketController;
import CommunicationLayer.NotificationController;
import ServiceLayer.Response;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Route(value = "addDiscount", layout = MainLayout.class)
@PreserveOnRefresh
public class AddDiscount extends VerticalLayout {
    private final Select<String> compositionType;
    private final TextField storeIdField;
    private final TextField valueField;
    private final IntegerField discountField;
    private final Button submitButton;
    private final MarketController marketController;

    @Autowired
    public AddDiscount() {
        Header header = new Header();
        header.setText("Add discount to store");


        Map<String, Runnable> actionsMap = new HashMap<>();
        actionsMap.put("Product", this::setForProductDiscount);
        actionsMap.put("Category", this::setForCategoryDiscount);
        actionsMap.put("Store", this::setForStoreDiscount);

        Select<String> discountType = new Select<>();
        discountType.setItems(actionsMap.keySet());
        discountType.addValueChangeListener(event -> actionsMap.get(event.getValue()).run());
        this.storeIdField = new TextField("store Id");
        this.valueField = new TextField();
        this.discountField = new IntegerField("price");
        discountField.setSuffixComponent(new Label("%"));
        discountField.setMin(0);
        discountField.setMax(100);
        compositionType = new Select<>();
        compositionType.setItems("Add", "Max");
        this.submitButton = new Button("add");
        this.marketController = MarketController.getInstance(new NotificationController());

        add(header, discountType, storeIdField, valueField, discountField, compositionType, submitButton);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void setForStoreDiscount() {
        valueField.setVisible(false);
        submitButton.addClickListener(e -> {
            int storeId = Integer.parseInt(storeIdField.getValue());
            double discountPercentage = discountField.getValue() / 100.0;
            int composition = Objects.equals(compositionType.getValue(), "Add") ? 0 : 1;
            Response r = marketController.addStoreDiscount(MainLayout.getSessionId(), storeId, discountPercentage, composition);
            if (r.getError_occurred())
                Notification.show(r.error_message);
            else
                Notification.show("discount created successfully");
        });
    }

    private void setForCategoryDiscount() {
        valueField.setVisible(true);
        valueField.setLabel("Category");
        submitButton.addClickListener(e -> {
            int storeId = Integer.parseInt(storeIdField.getValue());
            String category = valueField.getValue();
            double discountPercentage = discountField.getValue() / 100.0;
            int composition = Objects.equals(compositionType.getValue(), "Add") ? 0 : 1;
            Response r = marketController.addCategoryDiscount(MainLayout.getSessionId(), storeId, category, discountPercentage, composition);
            if (r.getError_occurred())
                Notification.show(r.error_message);
            else
                Notification.show("discount created successfully");
        });
    }

    private void setForProductDiscount() {
        valueField.setVisible(true);
        valueField.setLabel("ProductId");
        submitButton.addClickListener(e -> {
            int storeId = Integer.parseInt(storeIdField.getValue());
            int productId = Integer.parseInt(valueField.getValue());
            double discountPercentage = discountField.getValue() / 100.0;
            int composition = Objects.equals(compositionType.getValue(), "Add") ? 0 : 1;
            Response r = marketController.addProductDiscount(MainLayout.getSessionId(), storeId, productId, discountPercentage, composition);
            if (r.getError_occurred())
                Notification.show(r.error_message);
            else
                Notification.show("discount created successfully");
        });
    }


}

