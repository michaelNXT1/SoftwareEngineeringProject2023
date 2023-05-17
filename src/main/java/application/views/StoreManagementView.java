package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.Discounts.*;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.Discounts.ProductDiscountDTO;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route(value = "StoreManagementView", layout = MainLayout.class)
public class StoreManagementView extends VerticalLayout implements HasUrlParameter<String> {
    private final MarketController marketController;
    private final Header header;
    private Map<ProductDTO, Integer> productMap;
    private Map<DiscountDTO, List<BaseDiscountPolicyDTO>> discountPolicyMap;
    private Grid<ProductDiscountDTO> productDiscountGrid;
    private Grid<CategoryDiscountDTO> categoryDiscountGrid;
    private Grid<StoreDiscountDTO> storeDiscountGrid;
    private final Grid<ProductDTO> productGrid;

    @Autowired
    public StoreManagementView() {
        this.marketController = MarketController.getInstance();
        this.header = new Header();
        add(header);
        productGrid = new Grid<>(ProductDTO.class, false);
        productGrid.addColumn(ProductDTO::getProductId).setHeader("Id").setSortable(true).setTextAlign(ColumnTextAlign.START).setKey("Id");
        productGrid.addColumn(ProductDTO::getProductName).setHeader("Name").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(ProductDTO::getCategory).setHeader("Category").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(ProductDTO::getPrice).setHeader("Price").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(ProductDTO::getRating).setHeader("Rating").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.addColumn(productDTO -> productMap.get(productDTO)).setHeader("Quantity").setSortable(true).setTextAlign(ColumnTextAlign.START);
        productGrid.sort(List.of(new GridSortOrder<>(productGrid.getColumnByKey("Id"), SortDirection.ASCENDING)));
        add(new H1("Product List"), productGrid);
        HorizontalLayout discountGrids = new HorizontalLayout();

        InitProductDiscountGrid();
        InitCategoryDiscountGrid();
        InitStoreDiscountGrid();

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

    private void InitStoreDiscountGrid() {
        storeDiscountGrid = new Grid<>(StoreDiscountDTO.class, false);
        storeDiscountGrid.addColumn(StoreDiscountDTO::getDiscountPercentage).setHeader("Discount Percentage").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        storeDiscountGrid.addColumn(storeDiscountDTO -> {
                    StringBuilder ret = new StringBuilder();
                    List<BaseDiscountPolicyDTO> ls = discountPolicyMap.get(storeDiscountDTO);
                    if(ls.isEmpty())
                        return "none";
                    for (BaseDiscountPolicyDTO bdpDTO : ls) {
                        ret.append(bdpDTO.toString());
                        if (ls.indexOf(bdpDTO) != ls.size() - 1) {
                            ret.append("\nAND\n");
                        }
                    }
                    return ret.toString();
                }
        ).setHeader("Policy condition").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
    }

    private void InitCategoryDiscountGrid() {
        categoryDiscountGrid = new Grid<>(CategoryDiscountDTO.class, false);
        categoryDiscountGrid.addColumn(CategoryDiscountDTO::getCategory).setHeader("Category").setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setKey("Id");
        categoryDiscountGrid.addColumn(CategoryDiscountDTO::getDiscountPercentage).setHeader("Discount Percentage").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        categoryDiscountGrid.addColumn(categoryDiscountDTO -> {
                    StringBuilder ret = new StringBuilder();
                    List<BaseDiscountPolicyDTO> ls = discountPolicyMap.get(categoryDiscountDTO);
                    if(ls.isEmpty())
                        return "none";
                    for (BaseDiscountPolicyDTO bdpDTO : ls) {
                        ret.append(bdpDTO.toString());
                        if (ls.indexOf(bdpDTO) != ls.size() - 1) {
                            ret.append("\nAND\n");
                        }
                    }
                    return ret.toString();
                }
        ).setHeader("Policy condition").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
    }

    private void InitProductDiscountGrid() {
        productDiscountGrid = new Grid<>(ProductDiscountDTO.class, false);
        productDiscountGrid.addColumn(ProductDiscountDTO::getProductId).setHeader("Product Id").setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setKey("Id");
        productDiscountGrid.addColumn(ProductDiscountDTO::getDiscountPercentage).setHeader("Discount Percentage").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        productDiscountGrid.addColumn(productDiscountDTO -> {
                    StringBuilder ret = new StringBuilder();
                    List<BaseDiscountPolicyDTO> ls = discountPolicyMap.get(productDiscountDTO);
                    if(ls.isEmpty())
                        return "none";
                    for (BaseDiscountPolicyDTO bdpDTO : ls) {
                        ret.append(bdpDTO.toString());
                        if (ls.indexOf(bdpDTO) != ls.size() - 1) {
                            ret.append("\nAND\n");
                        }
                    }
                    return ret.toString();
                }
        ).setHeader("Policy condition").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
    }
}
