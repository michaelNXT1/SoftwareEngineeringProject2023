package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.Discounts.CategoryDiscountDTO;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Discounts.ProductDiscountDTO;
import ServiceLayer.DTOs.Discounts.StoreDiscountDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.ProductDTO;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Route(value = "StoreManagementView", layout = MainLayout.class)
public class StoreManagementView extends VerticalLayout implements HasUrlParameter<String> {
    private final MarketController marketController;
    private final Header header;
    private Map<ProductDTO, Integer> productMap;
    private Map<DiscountDTO, List<BaseDiscountPolicyDTO>> discountPolicyMap;
    private Grid<ProductDTO> productGrid;
    private Grid<BasePurchasePolicyDTO> purchasePolicyGrid;
    private Grid<ProductDiscountDTO> productDiscountGrid;
    private Grid<CategoryDiscountDTO> categoryDiscountGrid;
    private Grid<StoreDiscountDTO> storeDiscountGrid;
    private List<BasePurchasePolicyDTO> purchasePolicyList;

    @Autowired
    public StoreManagementView() {
        this.marketController = MarketController.getInstance();
        this.header = new Header();
        add(header);

        HorizontalLayout productAndPolicyGrids = new HorizontalLayout();
        VerticalLayout products = new VerticalLayout();
        initProductGrid();
        products.add(new H1("Product List"), productGrid);
        VerticalLayout purchasePolicies = new VerticalLayout();
        initPurchasePolicyGrid();
        purchasePolicies.add(new H1("Purchase Policy List"), purchasePolicyGrid);
        purchasePolicies.setSizeFull();
        productAndPolicyGrids.add(products, purchasePolicies);
        productAndPolicyGrids.setSizeFull();
        add(productAndPolicyGrids);

        HorizontalLayout discountGrids = new HorizontalLayout();
        initProductDiscountGrid();
        initCategoryDiscountGrid();
        initStoreDiscountGrid();
        discountGrids.add(productDiscountGrid, categoryDiscountGrid, storeDiscountGrid);
        add(new H1("Discount Lists"), discountGrids);

        discountGrids.setSizeFull();
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String parameter) {
        int storeId = Integer.parseInt(parameter);
        header.setText("Store Management: " + marketController.getStore(MainLayout.getSessionId(), storeId).getStoreName());
        productMap = marketController.getProductsByStore(storeId).value;
        productGrid.setItems(productMap.keySet().stream().toList());
        purchasePolicyList = marketController.getPurchasePoliciesByStoreId(storeId).value;
        purchasePolicyGrid.setItems(purchasePolicyList);
        discountPolicyMap = marketController.getDiscountPolicyMap(storeId).value;

        List<ProductDiscountDTO> productDiscountDTOS = new ArrayList<>();
        List<CategoryDiscountDTO> categoryDiscountDTOS = new ArrayList<>();
        List<StoreDiscountDTO> storeDiscountDTOS = new ArrayList<>();

        for (DiscountDTO pdDTO : discountPolicyMap.keySet()) {
            if (pdDTO instanceof ProductDiscountDTO) productDiscountDTOS.add((ProductDiscountDTO) pdDTO);
            else if (pdDTO instanceof CategoryDiscountDTO) categoryDiscountDTOS.add((CategoryDiscountDTO) pdDTO);
            else storeDiscountDTOS.add((StoreDiscountDTO) pdDTO);
        }
        productDiscountGrid.setItems(productDiscountDTOS);
        categoryDiscountGrid.setItems(categoryDiscountDTOS);
        storeDiscountGrid.setItems(storeDiscountDTOS);
    }

    private void initProductGrid() {
        productGrid = new Grid<>(ProductDTO.class, false);
        productGrid.addColumn(ProductDTO::getProductId).setHeader("Id").setSortable(true).setTextAlign(ColumnTextAlign.START).setKey("Id");
        productGrid.addColumn(ProductDTO::getProductName).setHeader("Name").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(ProductDTO::getCategory).setHeader("Category").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(ProductDTO::getPrice).setHeader("Price").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(ProductDTO::getRating).setHeader("Rating").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(productDTO -> productMap.get(productDTO)).setHeader("Quantity").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.sort(List.of(new GridSortOrder<>(productGrid.getColumnByKey("Id"), SortDirection.ASCENDING)));
    }

    private void initPurchasePolicyGrid() {
        purchasePolicyGrid = new Grid<>(BasePurchasePolicyDTO.class, false);
        purchasePolicyGrid.addColumn(basePurchasePolicyDTO -> purchasePolicyList.indexOf(basePurchasePolicyDTO)).setHeader("#").setSortable(true).setTextAlign(ColumnTextAlign.START);
        purchasePolicyGrid.addComponentColumn(purchasePolicy -> {
            Div div = new Div();
            div.getStyle().set("white-space", "pre-wrap");
            div.setText(purchasePolicy.toString());
            return div;
        }).setHeader("Policy Description").setSortable(true).setTextAlign(ColumnTextAlign.START);
    }

    private void initProductDiscountGrid() {
        productDiscountGrid = new Grid<>(ProductDiscountDTO.class, false);
        productDiscountGrid.addColumn(this::getProductName).setHeader("Product Name").setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setKey("Id");
        productDiscountGrid.addColumn(new NumberRenderer<>(DiscountDTO::getDiscountPercentage, NumberFormat.getPercentInstance())).setHeader("Discount Percentage").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        productDiscountGrid.addComponentColumn(this::getPolicyToString).setHeader("Policy condition").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
    }

    private void initCategoryDiscountGrid() {
        categoryDiscountGrid = new Grid<>(CategoryDiscountDTO.class, false);
        categoryDiscountGrid.addColumn(CategoryDiscountDTO::getCategory).setHeader("Category").setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setKey("Id");
        categoryDiscountGrid.addColumn(new NumberRenderer<>(DiscountDTO::getDiscountPercentage, NumberFormat.getPercentInstance())).setHeader("Discount Percentage").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        categoryDiscountGrid.addComponentColumn(this::getPolicyToString).setHeader("Policy condition").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
    }

    private void initStoreDiscountGrid() {
        storeDiscountGrid = new Grid<>(StoreDiscountDTO.class, false);
        storeDiscountGrid.addColumn(new NumberRenderer<>(DiscountDTO::getDiscountPercentage, NumberFormat.getPercentInstance())).setHeader("Discount Percentage").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        storeDiscountGrid.addComponentColumn(this::getPolicyToString).setHeader("Policy condition").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
    }

    private Div getPolicyToString(DiscountDTO discountDTO) {
        List<BaseDiscountPolicyDTO> ls = discountPolicyMap.get(discountDTO);
        StringBuilder ret = new StringBuilder();
        Div div = new Div();
        div.getStyle().set("white-space", "pre-wrap");
        div.setText("None");
        if (ls.isEmpty()) return div;
        for (BaseDiscountPolicyDTO bdpDTO : ls)
            ret.append(bdpDTO.toString() + (ls.indexOf(bdpDTO) != ls.size() - 1 ? "\nAND\n" : ""));
        div.setText(ret.toString());
        return div;
    }

    private String getProductName(ProductDiscountDTO productDiscountDTO) {
        return Objects.requireNonNull(productMap.keySet().stream().filter(productDTO -> productDTO.getProductId() == productDiscountDTO.getProductId()).findFirst().orElse(null)).getProductName();
    }
}
