package application.views.StoreManagement;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.*;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route(value = "StoreManagementView", layout = MainLayout.class)
@PreserveOnRefresh
public class StoreManagementView extends VerticalLayout implements HasUrlParameter<String> {
    private final MarketController marketController;
    private final Header header;
    private ProductManagementView products;
    private PurchasePolicyManagementView purchasePolicies;
    private EmployeeManagementView employees;
    private DiscountManagementView discountGrids;
    private OfferManagementView offers;
    private int storeId;

    @Autowired
    public StoreManagementView() {
        this.marketController = MarketController.getInstance();
        this.header = new Header();
        add(header);

        setWidthFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    public static void successMessage(Dialog dialog, Label errorSuccessLabel, String msg) {
        errorSuccessLabel.setText("");
        dialog.close();
        Dialog successDialog = new Dialog();
        VerticalLayout successVl = new VerticalLayout();
        successVl.add(new Label(msg), new Button("Close", e -> successDialog.close()));
        successDialog.add(successVl);
        successDialog.open();
    }

    private void viewPurchasesDialog() {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Store purchases");

        Grid<PurchaseDTO> purchaseGrid = new Grid<>(PurchaseDTO.class, false);
        ResponseT<List<PurchaseDTO>> purchaseListResponse = marketController.getPurchaseHistory(MainLayout.getSessionId(), storeId);
        purchaseGrid.setItems(purchaseListResponse.getError_occurred() ? new ArrayList<>() : purchaseListResponse.value);
        purchaseGrid.addColumn(PurchaseDTO::getPurchaseId).setHeader("#").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addColumn(PurchaseDTO::getPurchaseDateTime).setHeader("Purchase Date & Time").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addComponentColumn(purchaseDTO -> new Button("view details", e -> viewPurchaseDetailsDialog(purchaseDTO))).setFlexGrow(0).setAutoWidth(true);
        purchaseGrid.setWidthFull();

        VerticalLayout vl = new VerticalLayout(header, purchaseGrid);
        dialog.add(vl);
        dialog.setWidthFull();
        dialog.open();
    }

    private void viewPurchaseDetailsDialog(PurchaseDTO purchase) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Purchase #" + purchase.getPurchaseId());

        Grid<PurchaseProductDTO> purchaseGrid = new Grid<>(PurchaseProductDTO.class, false);
        purchaseGrid.setItems(purchase.getProductDTOList());
        purchaseGrid.addColumn(PurchaseProductDTO::getProductName).setHeader("Product name").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addColumn(purchaseProductDTO -> purchaseProductDTO.getPrice() + "§").setHeader("Price").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.addColumn(PurchaseProductDTO::getQuantity).setHeader("Quantity").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
        purchaseGrid.setWidthFull();

        VerticalLayout vl = new VerticalLayout(header, purchaseGrid);
        dialog.add(vl);
        dialog.setWidthFull();
        dialog.open();
    }

    private void CloseStore() {
        Response r = marketController.closeStore(MainLayout.getSessionId(), storeId);
        Notification.show(r.getError_occurred() ? r.error_message : "Store closed successfully", 3000, Notification.Position.MIDDLE);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String parameter) {
        storeId = Integer.parseInt(parameter);
        header.setText("Store Management: " + marketController.getStore(MainLayout.getSessionId(), storeId).value.getStoreName());

        products = new ProductManagementView(marketController, storeId);
        purchasePolicies = new PurchasePolicyManagementView(marketController, storeId);
        employees = new EmployeeManagementView(marketController, storeId);
        HorizontalLayout productAndPolicyGrids = new HorizontalLayout();
        products.setWidth("50%");
        productAndPolicyGrids.add(products, purchasePolicies);
        if (marketController.hasPermission(MainLayout.getSessionId(), storeId, PositionDTO.permissionType.EmployeeList).value) {
            purchasePolicies.setWidth("25%");
            employees.setWidth("25%");
            productAndPolicyGrids.add(employees);
        } else
            purchasePolicies.setWidth("50%");
        productAndPolicyGrids.setWidthFull();
        discountGrids = new DiscountManagementView(marketController, storeId);
        discountGrids.setWidthFull();

        offers = new OfferManagementView(marketController, storeId);
        offers.setWidthFull();

        add(
                productAndPolicyGrids,
                new H1("Discount Lists"),
                discountGrids,
                offers,
                new Button("View Purchases", e -> viewPurchasesDialog()),
                new Button("Close Store", e -> CloseStore())
        );

        Map<ProductDTO, Integer> productMap = marketController.getProductsByStore(storeId).value;
        products.setProductGrid(productMap.keySet().stream().toList());
        purchasePolicies.setPurchasePolicyGrid(marketController.getPurchasePoliciesByStoreId(storeId).value, productMap);
        ResponseT<List<MemberDTO>> employeeListResponse = marketController.getStoreEmployees(MainLayout.getSessionId(), storeId);
        List<MemberDTO> employeesList = employeeListResponse.getError_occurred() ? new ArrayList<>() : employeeListResponse.value;
        employees.setEmployeesList(employeesList);
        discountGrids.setGrids(productMap);
        List<OfferDTO> offersList = marketController.getOffersByStore(storeId).value;
        offers.setOfferGrid(offersList);
    }

}
