package application.views.StoreManagement;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.Response;
import application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PurchasePolicyManagementView extends VerticalLayout {
    private final int storeId;
    private final Grid<BasePurchasePolicyDTO> purchasePolicyGrid;
    private List<BasePurchasePolicyDTO> purchasePolicyList;
    private Map<ProductDTO, Integer> productMap;
    private final MarketController marketController;

    public PurchasePolicyManagementView(MarketController marketController, int storeId) {
        this.storeId = storeId;
        this.marketController = marketController;
        HorizontalLayout purchasePoliciesHL = new HorizontalLayout();
        Div purchasePoliciesDiv = new Div();
        purchasePoliciesHL.add(new H1("Purchase Policy List"), purchasePoliciesDiv, new Button("Join Policies", e -> JoinPurchasePoliciesDialog()), new Button("+", e -> addPurchasePolicyDialog()));
        purchasePoliciesHL.setFlexGrow(1, purchasePoliciesDiv);
        purchasePoliciesHL.setWidthFull();
        purchasePolicyGrid = new Grid<>(BasePurchasePolicyDTO.class, false);
        purchasePolicyGrid.addColumn(basePurchasePolicyDTO -> purchasePolicyList.indexOf(basePurchasePolicyDTO) + 1).setHeader("#").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(0);
        purchasePolicyGrid.addComponentColumn(purchasePolicy -> {
            Div div = new Div();
            div.getStyle().set("white-space", "pre-wrap");
            div.setText(purchasePolicy.toString());
            return div;
        }).setHeader("Policy Description").setSortable(true).setTextAlign(ColumnTextAlign.START);
        purchasePolicyGrid.addComponentColumn(purchasePolicy -> new Button("Remove", e -> removePurchasePolicyDialog(purchasePolicy.getPolicyId()))).setFlexGrow(0).setAutoWidth(true);
        add(purchasePoliciesHL, purchasePolicyGrid);
    }

    public void setPurchasePolicyGrid(List<BasePurchasePolicyDTO> basePurchasePolicyDTOList, Map<ProductDTO, Integer> productMap) {
        this.productMap = productMap;
        this.purchasePolicyList = basePurchasePolicyDTOList;
        purchasePolicyGrid.setItems(basePurchasePolicyDTOList);
    }

    private void addPurchasePolicyDialog() {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Add New Purchase Policy");
        Label errorSuccessLabel = new Label();
        Select<String> purchasePolicyTypeSelect = new Select<>();
        purchasePolicyTypeSelect.setItems(marketController.getPurchasePolicyTypes().value);
        purchasePolicyTypeSelect.setPlaceholder("Purchase policy type");
        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, purchasePolicyTypeSelect);

        //fields
        Select<String> categoryField = new Select<>();
        Select<String> productField = new Select<>();
        TimePicker startTime = new TimePicker();
        TimePicker endTime = new TimePicker();
        IntegerField quantityField = new IntegerField();
        Checkbox allowNone = new Checkbox();
        categoryField.setItems(marketController.getAllCategories().value);
        Map<String, Integer> productNameMap = productMap.keySet().stream().collect(Collectors.toMap(ProductDTO::getProductName, ProductDTO::getProductId));
        productField.setItems(productNameMap.keySet().stream().sorted().collect(Collectors.toList()));

        categoryField.setPlaceholder("Category");
        productField.setPlaceholder("Product");
        startTime.setPlaceholder("Start time");
        endTime.setPlaceholder("End time");
        allowNone.setLabel("Allow none");

        List<Component> components = new ArrayList<>();
        components.add(categoryField);
        components.add(productField);
        components.add(startTime);
        components.add(endTime);
        components.add(quantityField);
        components.add(allowNone);

        Button submitButton = new Button("Submit");
        submitButton.setEnabled(false);
        final Registration[] clickListener = new Registration[1];
        components.forEach(component -> component.setVisible(false));

        purchasePolicyTypeSelect.addValueChangeListener(e -> {
            submitButton.setEnabled(true);
            switch (e.getValue()) {
                case "Category Time Restriction" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    categoryField.setVisible(true);
                    startTime.setVisible(true);
                    endTime.setVisible(true);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.addCategoryTimeRestrictionPolicy(
                                MainLayout.getSessionId(),
                                storeId,
                                categoryField.getValue(),
                                startTime.getValue(),
                                endTime.getValue());
                        if (response.getError_occurred())
                            errorSuccessLabel.setText(response.error_message);
                        else
                            StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policy added successfully");
                    });
                }
                case "Product Max Quantity" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    productField.setVisible(true);
                    quantityField.setVisible(true);
                    quantityField.setPlaceholder("Max quantity");
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.addMaxQuantityPolicy(
                                MainLayout.getSessionId(),
                                storeId,
                                productNameMap.get(productField.getValue()),
                                quantityField.getValue());
                        if (response.getError_occurred())
                            errorSuccessLabel.setText(response.error_message);
                        else
                            StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policy added successfully");
                    });
                }
                case "Product Min Quantity" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    productField.setVisible(true);
                    quantityField.setVisible(true);
                    allowNone.setVisible(true);
                    quantityField.setPlaceholder("Min quantity");
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.addMinQuantityPolicy(
                                MainLayout.getSessionId(),
                                storeId,
                                productNameMap.get(productField.getValue()),
                                quantityField.getValue(),
                                allowNone.getValue());
                        if (response.getError_occurred())
                            errorSuccessLabel.setText(response.error_message);
                        else
                            StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policy added successfully");
                    });
                }
                case "Product Time Restriction" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    productField.setVisible(true);
                    startTime.setVisible(true);
                    endTime.setVisible(true);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.addProductTimeRestrictionPolicy(
                                MainLayout.getSessionId(),
                                storeId,
                                productNameMap.get(productField.getValue()),
                                startTime.getValue(),
                                endTime.getValue());
                        if (response.getError_occurred())
                            errorSuccessLabel.setText(response.error_message);
                        else
                            StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policy added successfully");
                    });
                }
                default -> {
                }
            }
        });
        vl.add(categoryField, productField, startTime, endTime, quantityField, allowNone, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();
    }

    private void JoinPurchasePoliciesDialog() {
        Map<String, Integer> purchasePolicyNameMap = purchasePolicyList.stream().collect(Collectors.toMap(BasePurchasePolicyDTO::toString, BasePurchasePolicyDTO::getPolicyId));
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Join Purchase Policies");
        Label errorSuccessLabel = new Label();
        Select<String> leftPurchaseSelect = new Select<>();
        Select<String> rightPurchaseSelect = new Select<>();
        Select<String> joinOperatorSelect = new Select<>();
        Div joinPreview = new Div();
        Button submitButton = new Button("Submit");

        joinPreview.getStyle().set("white-space", "pre-wrap");
        leftPurchaseSelect.setItems(purchasePolicyNameMap.keySet().stream().sorted().collect(Collectors.toList()));
        rightPurchaseSelect.setItems(purchasePolicyNameMap.keySet().stream().sorted().collect(Collectors.toList()));
        joinOperatorSelect.setItems("Or", "Conditional");
        leftPurchaseSelect.setPlaceholder("Policy #1");
        rightPurchaseSelect.setPlaceholder("Policy #2");
        joinOperatorSelect.setPlaceholder("Join Operator");

        leftPurchaseSelect.addValueChangeListener(e -> rightPurchaseSelect.setItemEnabledProvider(item -> !e.getValue().equals(item)));
        rightPurchaseSelect.addValueChangeListener(e -> leftPurchaseSelect.setItemEnabledProvider(item -> !e.getValue().equals(item)));
        joinOperatorSelect.addValueChangeListener(e -> {
            switch (e.getValue()) {
                case "Or" ->
                        joinPreview.setText(leftPurchaseSelect.getValue() + "\nOR\n" + rightPurchaseSelect.getValue());
                case "Conditional" ->
                        joinPreview.setText("If\n" + leftPurchaseSelect.getValue() + "\nis fulfilled`, then check\n" + rightPurchaseSelect.getValue());
            }
        });
        submitButton.addClickListener(e -> {
            int joinOperator = -1;
            switch (joinOperatorSelect.getValue()) {
                case "Or" -> joinOperator = 0;
                case "Conditional" -> joinOperator = 1;
            }
            Response response = marketController.joinPolicies(
                    MainLayout.getSessionId(),
                    storeId,
                    purchasePolicyNameMap.get(leftPurchaseSelect.getValue()),
                    purchasePolicyNameMap.get(rightPurchaseSelect.getValue()),
                    joinOperator);
            if (response.getError_occurred())
                errorSuccessLabel.setText(response.error_message);
            else
                StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policies joined successfully");
        });
        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, leftPurchaseSelect, rightPurchaseSelect, joinOperatorSelect, joinPreview, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();
    }

    private void removePurchasePolicyDialog(int policyId) {
        Dialog dialog = new Dialog();
        VerticalLayout vl = new VerticalLayout();
        Label errorSuccessLabel = new Label();
        Label label = new Label("Are you sure? This cannot be undone.");
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(
                new Button("Remove", e -> {
                    Response response = marketController.removePolicy(MainLayout.getSessionId(), storeId, policyId);
                    if (response.getError_occurred()) {
                        errorSuccessLabel.setText(response.error_message);
                    } else
                        StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policy removed successfully");
                }),
                new Button("Cancel", e -> dialog.close())
        );
        vl.add(errorSuccessLabel, label, hl);
        dialog.add(vl);
        dialog.open();
    }
}
