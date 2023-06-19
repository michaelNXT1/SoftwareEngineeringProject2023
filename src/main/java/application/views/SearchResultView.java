package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.BidDTO;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import Utils.Pair;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Route(value = "SearchResultView", layout = MainLayout.class)
@PreserveOnRefresh
public class
SearchResultView extends VerticalLayout {
    private final MarketController marketController;
    private final List<ProductDTO> items;
    private final Grid<ProductDTO> resultsGrid;
    private final Select<String> categorySelect;
    private final Select<String> priceSelect;
    private final List<Pair<String, Pair<Integer, Integer>>> priceList = List.of(
            new Pair<>("< 10", new Pair<>(0, 10)),
            new Pair<>("10-20", new Pair<>(10, 20)),
            new Pair<>("20-30", new Pair<>(20, 30)),
            new Pair<>("30-40", new Pair<>(30, 40)),
            new Pair<>("40-50", new Pair<>(40, 50)),
            new Pair<>("50-60", new Pair<>(50, 60)),
            new Pair<>("60-70", new Pair<>(60, 70)),
            new Pair<>("70-80", new Pair<>(70, 80)),
            new Pair<>("80-90", new Pair<>(80, 90)),
            new Pair<>("90-100", new Pair<>(90, 100)),
            new Pair<>("100 <", new Pair<>(100, Integer.MAX_VALUE)));
    private final Map<String, Pair<Integer, Integer>> priceMap = priceList.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

    @Autowired
    public SearchResultView() {
        this.marketController = MarketController.getInstance();
        Header header = new Header();
        header.setText("Search results for " + marketController.getSearchKeyword(MainLayout.getSessionId()).value);
        add(header);

        ResponseT<List<ProductDTO>> r = marketController.getSearchResults(MainLayout.getSessionId());
        if (r.getError_occurred())
            System.out.println("here");
        items = r.value;
        HorizontalLayout filters = new HorizontalLayout();
        categorySelect = initCategorySelect();
        priceSelect = initPriceSelect();
        filters.add(categorySelect, priceSelect);

        resultsGrid = initResultsGrid();
        add(filters, resultsGrid);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private Grid<ProductDTO> initResultsGrid() {
        final Grid<ProductDTO> grid;
        grid = new Grid<>(ProductDTO.class, false);
        grid.addColumn(ProductDTO::getProductName).setHeader("Product Name");
        grid.addColumn(ProductDTO::getCategory).setHeader("Category");
        grid.addColumn(this::getStoreName).setHeader("Store Name");
        grid.addColumn(productDTO -> {
            if (productDTO.getPurchaseType() != ProductDTO.PurchaseType.AUCTION)
                return productDTO.getPrice() + "§";
            return productDTO.getBidders().isEmpty() ? productDTO.getPrice()+"§" : productDTO.getBidders().stream().max(Comparator.comparingDouble(BidDTO::getOfferedPrice)).orElse(null).getOfferedPrice()+"§";
        }).setHeader("Price");
        grid.addComponentColumn(product -> {
            HorizontalLayout hl = new HorizontalLayout();
            IntegerField quantity = new IntegerField();
            quantity.setValue(1);
            quantity.setStepButtonsVisible(true);
            quantity.setMin(1);
            Button purchaseButton = new Button();
            switch (product.getPurchaseType()) {
                case BUY_IT_NOW ->
                        purchaseButton = new Button("Add to Cart", e -> addToCart(product, quantity.getValue()));
                case OFFER ->
                        purchaseButton = new Button("Make Offer", e -> makeOfferDialog(product, quantity.getValue()));
                case AUCTION -> purchaseButton = new Button("Bid", e -> bidDialog(product, quantity.getValue()));
            }
            hl.add(quantity, purchaseButton);
            return hl;
        });
        grid.setItems(items);
        grid.setVisible(!items.isEmpty());
        return grid;
    }

    private void bidDialog(ProductDTO product, Integer value) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        String ret=product.getBidders().isEmpty() ? product.getPrice()+"§" : product.getBidders().stream().max(Comparator.comparingDouble(BidDTO::getOfferedPrice)).orElse(null).getOfferedPrice()+"§";
        header.setText("Bid for " + product.getProductName()+" (you should bid more than "+ret+")");
        Label errorSuccessLabel = new Label();
        NumberField priceField = new NumberField();
        Button submitButton = new Button("Commit to Pay", event -> {
            if (priceField.getValue() == null)
                errorSuccessLabel.setText("Price can't be empty");
            else {
                Response response = marketController.bid(
                        MainLayout.getSessionId(),
                        product.getStoreId(),
                        product.getProductId(),
                        priceField.getValue());
                if (response.getError_occurred())
                    errorSuccessLabel.setText(response.error_message);
                else {
                    errorSuccessLabel.setText("");
                    dialog.close();
                    Dialog successDialog = new Dialog();
                    VerticalLayout successVl = new VerticalLayout();
                    successVl.add(new Label("Bid offer made successfully!"), new Button("Close", e -> successDialog.close()));
                    successDialog.add(successVl);
                    successDialog.open();
                }
            }
        });

        Span paymentDetails = paymentDetailsErr(submitButton);
        Span deliveryAddress = deliveryAddressErr(submitButton);

        priceField.setPlaceholder("Price");

        priceField.setLabel("Price");

        priceField.setValue(product.getPrice());

        Label totalLabel = new Label("You will pay a total of " + priceField.getValue() + "§");

        priceField.addValueChangeListener(r -> totalLabel.setText("You will pay a total of " + priceField.getValue() + "§"));

        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, priceField, totalLabel, submitButton, paymentDetails, deliveryAddress);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dialog.add(vl);
        dialog.open();
    }

    private void makeOfferDialog(ProductDTO product, Integer quantity) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Make offer for " + product.getProductName());
        Label errorSuccessLabel = new Label();
        IntegerField quantityField = new IntegerField();
        NumberField priceField = new NumberField();
        Button submitButton = new Button("Commit to Pay", event -> {
            if (priceField.getValue() == null)
                errorSuccessLabel.setText("Price can't be empty");
            else if (quantityField.getValue() == null)
                errorSuccessLabel.setText("Quantity can't be empty");
            else {
                Response response = marketController.makeOffer(
                        MainLayout.getSessionId(),
                        product.getStoreId(),
                        product.getProductId(),
                        priceField.getValue(),
                        quantityField.getValue());
                if (response.getError_occurred())
                    errorSuccessLabel.setText(response.error_message);
                else {
                    errorSuccessLabel.setText("");
                    dialog.close();
                    Dialog successDialog = new Dialog();
                    VerticalLayout successVl = new VerticalLayout();
                    successVl.add(new Label("offer made successfully!"), new Button("Close", e -> successDialog.close()));
                    successDialog.add(successVl);
                    successDialog.open();
                }
            }
        });

        Span paymentDetails = paymentDetailsErr(submitButton);
        Span deliveryAddress = deliveryAddressErr(submitButton);

        priceField.setPlaceholder("Price");
        quantityField.setPlaceholder("Quantity");

        priceField.setLabel("Price per item");
        quantityField.setLabel("Quantity");

        priceField.setValue(product.getPrice());
        quantityField.setValue(quantity);

        Label totalLabel = new Label("You will pay a total of " + quantityField.getValue() * priceField.getValue() + "§");

        priceField.addValueChangeListener(r -> totalLabel.setText("You will pay a total of " + quantityField.getValue() * priceField.getValue()));
        quantityField.addValueChangeListener(r -> totalLabel.setText("You will pay a total of " + quantityField.getValue() * priceField.getValue()));

        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, priceField, quantityField, totalLabel, submitButton, paymentDetails, deliveryAddress);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dialog.add(vl);
        dialog.open();
    }

    private Span paymentDetailsErr(Button submitButton) {
        Span paymentDetails = new Span("Please add payment details.");
        ResponseT<Boolean> paymentDetailsExist = marketController.hasPaymentMethod(MainLayout.getSessionId());
        if (paymentDetailsExist.getError_occurred()) {
            submitButton.setEnabled(false);
            paymentDetails.setText(paymentDetailsExist.error_message);
        } else {
            if (!paymentDetailsExist.value)
                submitButton.setEnabled(false);
            paymentDetails.setVisible(!paymentDetailsExist.value);
        }
        return paymentDetails;
    }

    private Span deliveryAddressErr(Button submitButton) {
        Span deliveryAddress = new Span("Please add delivery address.");
        ResponseT<Boolean> deliveryAddressExists = marketController.hasDeliveryAddress(MainLayout.getSessionId());
        if (deliveryAddressExists.getError_occurred()) {
            submitButton.setEnabled(false);
            deliveryAddress.setText(deliveryAddressExists.error_message);
        } else {
            if (!deliveryAddressExists.value)
                submitButton.setEnabled(false);
            deliveryAddress.setVisible(!deliveryAddressExists.value);
        }
        return deliveryAddress;
    }

    private Select<String> initCategorySelect() {
        Select<String> categorySelect = new Select<>();
        List<String> categoryOptions = new ArrayList<>();
        categoryOptions.add("All");
        categoryOptions.addAll(items.stream().map(ProductDTO::getCategory).collect(Collectors.toSet()).stream().sorted().toList());
        categorySelect.setItems(categoryOptions);
        categorySelect.setPlaceholder("By Category");
        categorySelect.addValueChangeListener(e -> resultsGrid.setItems(filterResults(e.getValue(), priceSelect.getValue())));
        return categorySelect;
    }

    private Select<String> initPriceSelect() {
        Select<String> priceSelect = new Select<>();
        List<String> priceOptions = new ArrayList<>();
        priceOptions.add("All");
        priceOptions.addAll(priceList.stream().filter(e -> items.stream().map(ProductDTO::getPrice).anyMatch(number -> number > e.getSecond().getFirst() && number < e.getSecond().getSecond())).map(Pair::getFirst).toList());
        priceSelect.setItems(priceOptions);
        priceSelect.setPlaceholder("By Price Range");
        priceSelect.addValueChangeListener(e -> resultsGrid.setItems(filterResults(categorySelect.getValue(), e.getValue())));
        return priceSelect;
    }

    private List<ProductDTO> filterResults(String category, String price) {
        List<ProductDTO> newItems = items.stream().toList();
        if (!Objects.equals(category, "All") && category != null)
            newItems = newItems.stream().filter(productDTO -> Objects.equals(productDTO.getCategory(), category)).collect(Collectors.toList());
        if (!Objects.equals(price, "All") && price != null)
            newItems = newItems.stream().filter(productDTO -> productDTO.getPrice() > priceMap.get(price).getFirst() && productDTO.getPrice() < priceMap.get(price).getSecond()).toList();
        return newItems;
    }

    private String getStoreName(ProductDTO p) {
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
