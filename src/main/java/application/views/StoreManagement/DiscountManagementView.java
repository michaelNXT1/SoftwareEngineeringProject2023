package application.views.StoreManagement;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.Discounts.CategoryDiscountDTO;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Discounts.ProductDiscountDTO;
import ServiceLayer.DTOs.Discounts.StoreDiscountDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.Response;
import application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.shared.Registration;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DiscountManagementView extends HorizontalLayout {
    private final int storeId;
    private Map<ProductDTO, Integer> productMap;
    private Map<DiscountDTO, List<BaseDiscountPolicyDTO>> discountPolicyMap;
    private Grid<ProductDiscountDTO> productDiscountGrid;
    private Grid<CategoryDiscountDTO> categoryDiscountGrid;
    private Grid<StoreDiscountDTO> storeDiscountGrid;
    private final MarketController marketController;

    public DiscountManagementView(MarketController marketController, int storeId) {
        this.storeId = storeId;
        this.marketController = marketController;
        VerticalLayout productDiscountLayout = initProductDiscountGrid();
        VerticalLayout categoryDiscountLayout = initCategoryDiscountGrid();
        VerticalLayout storeDiscountLayout = initStoreDiscountGrid();
        add(productDiscountLayout, categoryDiscountLayout, storeDiscountLayout);
    }

    public void setGrids(Map<ProductDTO, Integer> productMap) {
        this.productMap=productMap;
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

    private VerticalLayout initProductDiscountGrid() {
        VerticalLayout productDiscountLayout = new VerticalLayout();
        HorizontalLayout productDiscountHL = new HorizontalLayout();
        Div div = new Div();
        productDiscountHL.add(new H2("Product Discounts"), div, new Button("+", e -> addProductDiscountDialog()));
        productDiscountHL.setFlexGrow(1, div);
        productDiscountHL.setWidthFull();
        productDiscountGrid = new Grid<>(ProductDiscountDTO.class, false);
        productDiscountGrid.addColumn(this::getProductName).setHeader("Product Name").setSortable(true);
        productDiscountGrid.addColumn(new NumberRenderer<>(DiscountDTO::getDiscountPercentage, NumberFormat.getPercentInstance())).setHeader(generateColumnHeader()).setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        productDiscountGrid.addComponentColumn(this::getPolicyToString).setHeader("Policy condition").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        productDiscountGrid.addComponentColumn(discountDTO -> new Button("Modify", e -> modifyDiscountDialog(discountDTO))).setFlexGrow(0).setAutoWidth(true);
        productDiscountLayout.add(productDiscountHL, productDiscountGrid);
        return productDiscountLayout;
    }

    private VerticalLayout initCategoryDiscountGrid() {
        VerticalLayout categoryDiscountLayout = new VerticalLayout();
        HorizontalLayout categoryDiscountHL = new HorizontalLayout();
        Div div = new Div();
        categoryDiscountHL.add(new H2("Category Discounts"), div, new Button("+", e -> addCategoryDiscountDialog()));
        categoryDiscountHL.setFlexGrow(1, div);
        categoryDiscountHL.setWidthFull();
        categoryDiscountGrid = new Grid<>(CategoryDiscountDTO.class, false);
        categoryDiscountGrid.addColumn(CategoryDiscountDTO::getCategory).setHeader("Category").setSortable(true).setTextAlign(ColumnTextAlign.CENTER).setKey("Id");
        categoryDiscountGrid.addColumn(new NumberRenderer<>(DiscountDTO::getDiscountPercentage, NumberFormat.getPercentInstance())).setHeader(generateColumnHeader()).setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        categoryDiscountGrid.addComponentColumn(this::getPolicyToString).setHeader("Policy condition").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        categoryDiscountGrid.addComponentColumn(discountDTO -> new Button("Modify", e -> modifyDiscountDialog(discountDTO))).setFlexGrow(0).setAutoWidth(true);
        categoryDiscountLayout.add(categoryDiscountHL, categoryDiscountGrid);
        return categoryDiscountLayout;
    }

    private VerticalLayout initStoreDiscountGrid() {
        VerticalLayout storeDiscountLayout = new VerticalLayout();
        HorizontalLayout storeDiscountHL = new HorizontalLayout();
        Div div = new Div();
        storeDiscountHL.add(new H2("Store Discounts"), div, new Button("+", e -> addStoreDiscountDialog()));
        storeDiscountHL.setFlexGrow(1, div);
        storeDiscountHL.setWidthFull();
        storeDiscountGrid = new Grid<>(StoreDiscountDTO.class, false);
        storeDiscountGrid.addColumn(new NumberRenderer<>(DiscountDTO::getDiscountPercentage, NumberFormat.getPercentInstance())).setHeader(generateColumnHeader()).setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        storeDiscountGrid.addComponentColumn(this::getPolicyToString).setHeader("Policy condition").setSortable(true).setTextAlign(ColumnTextAlign.CENTER);
        storeDiscountGrid.addComponentColumn(discountDTO -> new Button("Modify", e -> modifyDiscountDialog(discountDTO))).setFlexGrow(0).setAutoWidth(true);
        storeDiscountLayout.add(storeDiscountHL, storeDiscountGrid);
        return storeDiscountLayout;
    }

    private void addProductDiscountDialog() {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Add New Product Discount");
        Label errorSuccessLabel = new Label();
        Select<String> productField = new Select<>();
        IntegerField discountPercentageField = new IntegerField();
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setLabel("Travel class");
        radioGroup.setItems("Addition", "Max Discount");

        Button cancelButton = new Button("Cancel", event -> dialog.close());

        Map<String, Integer> productNameMap = productMap.keySet().stream().collect(Collectors.toMap(ProductDTO::getProductName, ProductDTO::getProductId));
        productField.setItems(productNameMap.keySet().stream().sorted().collect(Collectors.toList()));
        productField.setPlaceholder("Product Name");
        discountPercentageField.setPlaceholder("Discount Percentage");

        discountPercentageField.setMin(0);
        discountPercentageField.setMax(100);

        Button submitButton = new Button("Submit", event -> {
            if (discountPercentageField.getValue() == null)
                errorSuccessLabel.setText("Discount percentage can't be empty");
            else {
                int compositionType = -1;
                switch (radioGroup.getValue()) {
                    case "Addition" -> compositionType = 0;
                    case "Max Discount" -> compositionType = 1;
                }
                Response response = marketController.addProductDiscount(
                        MainLayout.getSessionId(),
                        storeId,
                        productNameMap.get(productField.getValue()),
                        (double) (discountPercentageField.getValue()) / 100.0,
                        compositionType);
                if (response.getError_occurred())
                    errorSuccessLabel.setText(response.error_message);
                else
                    StoreManagementView.successMessage(dialog, errorSuccessLabel, "Discount added successfully!");
            }
        });

        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, productField, discountPercentageField, radioGroup, submitButton, cancelButton);
        dialog.add(vl);
        dialog.open();
    }

    private void addCategoryDiscountDialog() {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Add New Category Discount");
        Label errorSuccessLabel = new Label();
        Select<String> categoryField = new Select<>();
        IntegerField discountPercentageField = new IntegerField();
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setLabel("Travel class");
        radioGroup.setItems("Addition", "Max Discount");

        Button cancelButton = new Button("Cancel", event -> dialog.close());
        categoryField.setItems(marketController.getAllCategories().value);
        categoryField.setPlaceholder("Category Name");
        discountPercentageField.setPlaceholder("Discount Percentage");

        discountPercentageField.setMin(0);
        discountPercentageField.setMax(100);

        Button submitButton = new Button("Submit", event -> {
            if (discountPercentageField.getValue() == null)
                errorSuccessLabel.setText("Discount percentage can't be empty");
            else {
                int compositionType = -1;
                switch (radioGroup.getValue()) {
                    case "Addition" -> compositionType = 0;
                    case "Max Discount" -> compositionType = 1;
                }
                Response response = marketController.addCategoryDiscount(
                        MainLayout.getSessionId(),
                        storeId,
                        categoryField.getValue(),
                        (double) (discountPercentageField.getValue()) / 100.0,
                        compositionType);
                if (response.getError_occurred())
                    errorSuccessLabel.setText(response.error_message);
                else
                    StoreManagementView.successMessage(dialog, errorSuccessLabel, "Discount added successfully!");
            }
        });

        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, categoryField, discountPercentageField, radioGroup, submitButton, cancelButton);
        dialog.add(vl);
        dialog.open();
    }

    private void addStoreDiscountDialog() {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Add New Category Discount");
        Label errorSuccessLabel = new Label();
        IntegerField discountPercentageField = new IntegerField();
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setLabel("Travel class");
        radioGroup.setItems("Addition", "Max Discount");

        Button cancelButton = new Button("Cancel", event -> dialog.close());
        discountPercentageField.setPlaceholder("Discount Percentage");

        discountPercentageField.setMin(0);
        discountPercentageField.setMax(100);

        Button submitButton = new Button("Submit", event -> {
            if (discountPercentageField.getValue() == null)
                errorSuccessLabel.setText("Discount percentage can't be empty");
            else {
                int compositionType = -1;
                switch (radioGroup.getValue()) {
                    case "Addition" -> compositionType = 0;
                    case "Max Discount" -> compositionType = 1;
                }
                Response response = marketController.addStoreDiscount(
                        MainLayout.getSessionId(),
                        storeId,
                        (double) (discountPercentageField.getValue()) / 100.0,
                        compositionType);
                if (response.getError_occurred())
                    errorSuccessLabel.setText(response.error_message);
                else
                    StoreManagementView.successMessage(dialog, errorSuccessLabel, "Discount added successfully!");
            }
        });

        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, discountPercentageField, radioGroup, submitButton, cancelButton);
        dialog.add(vl);
        dialog.open();
    }

    private void modifyDiscountDialog(DiscountDTO discount) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Modify Discount");
        Button addPolicyButton = new Button("Add new Discount Policy", event -> {
            dialog.close();
            addDiscountPolicyDialog(discount);
        });
        Button joinButton = new Button("Join Policies", event -> {
            dialog.close();
            joinDiscountPolicies(discount);
        });
        Button removePolicyButton = new Button("Remove Policy", event -> {
            dialog.close();
            removeDiscountPolicy(discount);
        });
        Button removeButton = new Button("Remove Discount", event -> {
            Dialog dialog2 = new Dialog();
            VerticalLayout vl2 = new VerticalLayout();
            Label errorSuccessLabel = new Label();
            Label label = new Label("Are you sure? This cannot be undone.");
            HorizontalLayout hl = new HorizontalLayout();
            hl.add(
                    new Button("Remove", e -> {
                        Response response = marketController.removeDiscount(MainLayout.getSessionId(), storeId, Math.toIntExact(discount.getDiscountId()));
                        if (response.getError_occurred()) {
                            errorSuccessLabel.setText(response.error_message);
                        } else
                            StoreManagementView.successMessage(dialog2, errorSuccessLabel, "Discount removed successfully");
                        dialog.close();
                    }),
                    new Button("Cancel", e -> dialog2.close())
            );
            vl2.add(errorSuccessLabel, label, hl);
            dialog2.add(vl2);
            dialog2.open();
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());

        VerticalLayout vl = new VerticalLayout();
        vl.add(header, addPolicyButton, joinButton, removePolicyButton, removeButton, cancelButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();
    }

    private String getProductName(ProductDiscountDTO productDiscountDTO) {
        return Objects.requireNonNull(productMap.keySet().stream().filter(productDTO -> productDTO.getProductId() == productDiscountDTO.getProductId()).findFirst().orElse(null)).getProductName();
    }

    private void addDiscountPolicyDialog(DiscountDTO discount) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Add New Discount Policy");
        Label errorSuccessLabel = new Label();
        Select<String> discountPolicyTypeSelect = new Select<>();
        discountPolicyTypeSelect.setItems(marketController.getDiscountPolicyTypes().value);
        discountPolicyTypeSelect.setPlaceholder("Discount policy type");
        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, discountPolicyTypeSelect);

        //fields
        Select<String> productField = new Select<>();
        IntegerField quantityField = new IntegerField();
        NumberField bagTotalField = new NumberField();
        Checkbox allowNone = new Checkbox();
        Map<String, Integer> productNameMap = productMap.keySet().stream().collect(Collectors.toMap(ProductDTO::getProductName, ProductDTO::getProductId));
        productField.setItems(productNameMap.keySet().stream().sorted().collect(Collectors.toList()));

        productField.setPlaceholder("Product");
        bagTotalField.setPlaceholder("Bag total");
        allowNone.setLabel("Allow none");

        List<Component> components = new ArrayList<>();
        components.add(productField);
        components.add(bagTotalField);
        components.add(allowNone);

        Button submitButton = new Button("Submit");
        submitButton.setEnabled(false);
        final Registration[] clickListener = new Registration[1];
        components.forEach(component -> component.setVisible(false));

        discountPolicyTypeSelect.addValueChangeListener(e -> {
            submitButton.setEnabled(true);
            switch (e.getValue()) {
                case "Product Max Quantity" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    productField.setVisible(true);
                    quantityField.setVisible(true);
                    quantityField.setPlaceholder("Max quantity");
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.addMaxQuantityDiscountPolicy(
                                MainLayout.getSessionId(),
                                storeId,
                                Math.toIntExact(discount.getDiscountId()),
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
                    quantityField.setPlaceholder("Min quantity");
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.addMinQuantityDiscountPolicy(
                                MainLayout.getSessionId(),
                                storeId,
                                Math.toIntExact(discount.getDiscountId()),
                                productNameMap.get(productField.getValue()),
                                quantityField.getValue(),
                                allowNone.getValue());
                        if (response.getError_occurred())
                            errorSuccessLabel.setText(response.error_message);
                        else
                            StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policy added successfully");
                    });
                }
                case "Min Bag Total" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    bagTotalField.setVisible(true);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.addMinBagTotalDiscountPolicy(
                                MainLayout.getSessionId(),
                                storeId,
                                Math.toIntExact(discount.getDiscountId()),
                                bagTotalField.getValue());
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
        vl.add(productField, bagTotalField, quantityField, allowNone, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();

    }

    private void joinDiscountPolicies(DiscountDTO discount) {
        Map<String, Integer> discountPolicyNameMap = discountPolicyMap.get(discount).stream().collect(Collectors.toMap(BaseDiscountPolicyDTO::toString, BaseDiscountPolicyDTO::getPolicyId));
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Join Discount Policies");
        Label errorSuccessLabel = new Label();
        Select<String> leftPolicySelect = new Select<>();
        Select<String> rightPolicySelect = new Select<>();
        Select<String> joinOperatorSelect = new Select<>();
        Div joinPreview = new Div();
        Button submitButton = new Button("Submit");

        joinPreview.getStyle().set("white-space", "pre-wrap");
        leftPolicySelect.setItems(discountPolicyNameMap.keySet().stream().sorted().collect(Collectors.toList()));
        rightPolicySelect.setItems(discountPolicyNameMap.keySet().stream().sorted().collect(Collectors.toList()));
        joinOperatorSelect.setItems("Or", "Xor");
        leftPolicySelect.setPlaceholder("Policy #1");
        rightPolicySelect.setPlaceholder("Policy #2");
        joinOperatorSelect.setPlaceholder("Join Operator");

        leftPolicySelect.addValueChangeListener(e -> rightPolicySelect.setItemEnabledProvider(item -> !e.getValue().equals(item)));
        rightPolicySelect.addValueChangeListener(e -> leftPolicySelect.setItemEnabledProvider(item -> !e.getValue().equals(item)));
        joinOperatorSelect.addValueChangeListener(e -> {
            switch (e.getValue()) {
                case "Or" -> joinPreview.setText(leftPolicySelect.getValue() + "\nOR\n" + rightPolicySelect.getValue());
                case "Xor" ->
                        joinPreview.setText(leftPolicySelect.getValue() + "\nOR\n" + rightPolicySelect.getValue() + "\nbut not both.");
            }
        });
        submitButton.addClickListener(e -> {
            int joinOperator = -1;
            switch (joinOperatorSelect.getValue()) {
                case "Or" -> joinOperator = 0;
                case "Xor" -> joinOperator = 1;
            }
            Response response = marketController.joinDiscountPolicies(
                    MainLayout.getSessionId(),
                    storeId,
                    discountPolicyNameMap.get(leftPolicySelect.getValue()),
                    discountPolicyNameMap.get(rightPolicySelect.getValue()),
                    joinOperator);
            if (response.getError_occurred())
                errorSuccessLabel.setText(response.error_message);
            else
                StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policies joined successfully");
        });
        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, leftPolicySelect, rightPolicySelect, joinOperatorSelect, joinPreview, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();

    }

    private void removeDiscountPolicy(DiscountDTO discount) {
        Map<String, Integer> discountPolicyNameMap = discountPolicyMap.get(discount).stream().collect(Collectors.toMap(BaseDiscountPolicyDTO::toString, BaseDiscountPolicyDTO::getPolicyId));
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Remove Discount Policy");
        Label errorSuccessLabel = new Label();
        Select<String> policySelect = new Select<>();
        policySelect.setItems(discountPolicyNameMap.keySet().stream().sorted().collect(Collectors.toList()));
        policySelect.setPlaceholder("Policy");
        Button submitButton = new Button("Submit");
        submitButton.addClickListener(e -> {
            Response response = marketController.removeDiscountPolicy(
                    MainLayout.getSessionId(),
                    storeId,
                    discountPolicyNameMap.get(policySelect.getValue()));
            if (response.getError_occurred())
                errorSuccessLabel.setText(response.error_message);
            else
                StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policy removed successfully");
        });
        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, policySelect, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();

    }

    private Div getPolicyToString(DiscountDTO discountDTO) {
        List<BaseDiscountPolicyDTO> ls = discountPolicyMap.get(discountDTO);
        StringBuilder ret = new StringBuilder();
        Div div = new Div();
        div.getStyle().set("white-space", "pre-wrap");
        div.setText("None");
        if (ls.isEmpty()) return div;
        for (BaseDiscountPolicyDTO bdpDTO : ls)
            ret.append(bdpDTO.toString()).append(ls.indexOf(bdpDTO) != ls.size() - 1 ? "\nAND\n" : "");
        div.setText(ret.toString());
        return div;
    }

    private Span generateColumnHeader() {
        Span header = new Span("Discount<br/>Percentage");
        header.getElement().setProperty("innerHTML", "Discount<br/>Percentage");
        return header;
    }
}
