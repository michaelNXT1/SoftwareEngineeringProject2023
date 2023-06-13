package application.views;

import CommunicationLayer.MarketController;
import CommunicationLayer.NotificationController;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import Utils.Pair;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    public SearchResultView() throws Exception {
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
        });
        grid.setItems(items);
        grid.setVisible(!items.isEmpty());
        return grid;
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
