package application.views;

import CommunicationLayer.MarketController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Route(value = "addPaymentMethod", layout = MainLayout.class)
@PreserveOnRefresh
public class AddPaymentMethod extends VerticalLayout {
    private final TextField cardNumberField;
    private final Select<String> monthSelect;
    private final Select<String> yearSelect;
    private final TextField cvvField;
    private final Button submitButton;
    private final MarketController marketController;
    private final Header header;

    @Autowired
    public AddPaymentMethod() {
        this.header = new Header();
        this.header.setText("Add Payment Method");
        this.cardNumberField = new TextField("Card Number");
        this.monthSelect = new Select<>();
        monthSelect.setItems(IntStream.rangeClosed(1, 12).mapToObj(Integer::toString).collect(Collectors.toList()));
        monthSelect.setPlaceholder("Month");
        yearSelect = new Select<>();
        yearSelect.setItems(IntStream.rangeClosed(LocalDate.now().getYear(), LocalDate.now().getYear() + 10).mapToObj(Integer::toString).collect(Collectors.toList()));
        yearSelect.setPlaceholder("Year");
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(monthSelect, yearSelect);
        this.cvvField = new TextField("cvv");
        this.submitButton = new Button("add", e -> addPaymentMethod());
        this.marketController = MarketController.getInstance();

        add(header, cardNumberField, hl, cvvField, submitButton);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void addPaymentMethod() {
        String cardNumber = cardNumberField.getValue();
        String month = monthSelect.getValue();
        String year = yearSelect.getValue();
        String cvv = cvvField.getValue();
        Response response = marketController.addPaymentMethod(MainLayout.getSessionId(), cardNumber, month, year, cvv);
        if (response.getError_occurred())
            Notification.show(response.error_message);
    }
}
