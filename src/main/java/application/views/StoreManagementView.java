package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.*;
import ServiceLayer.DTOs.Discounts.CategoryDiscountDTO;
import ServiceLayer.DTOs.Discounts.DiscountDTO;
import ServiceLayer.DTOs.Discounts.ProductDiscountDTO;
import ServiceLayer.DTOs.Discounts.StoreDiscountDTO;
import ServiceLayer.DTOs.Policies.DiscountPolicies.BaseDiscountPolicyDTO;
import ServiceLayer.DTOs.Policies.PurchasePolicies.BasePurchasePolicyDTO;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "StoreManagementView", layout = MainLayout.class)
@PreserveOnRefresh
public class StoreManagementView extends VerticalLayout implements HasUrlParameter<String> {
    private final MarketController marketController;
    private final Header header;
    private Map<ProductDTO, Integer> productMap;
    private List<MemberDTO> employeesList;
    private List<BasePurchasePolicyDTO> purchasePolicyList;
    private Map<DiscountDTO, List<BaseDiscountPolicyDTO>> discountPolicyMap;
    private Grid<ProductDTO> productGrid;
    private Grid<BasePurchasePolicyDTO> purchasePolicyGrid;
    private Grid<MemberDTO> employeesGrid;
    private Grid<ProductDiscountDTO> productDiscountGrid;
    private Grid<CategoryDiscountDTO> categoryDiscountGrid;
    private Grid<StoreDiscountDTO> storeDiscountGrid;
    private int storeId;

    @Autowired
    public StoreManagementView() throws Exception {
        this.marketController = MarketController.getInstance();
        this.header = new Header();
        add(header);

        VerticalLayout products = initProductGrid();
        VerticalLayout purchasePolicies = initPurchasePolicyGrid();
        VerticalLayout employees = initEmployeesGrid();
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

        VerticalLayout productDiscountLayout = initProductDiscountGrid();
        VerticalLayout categoryDiscountLayout = initCategoryDiscountGrid();
        VerticalLayout storeDiscountLayout = initStoreDiscountGrid();
        HorizontalLayout discountGrids = new HorizontalLayout();
        discountGrids.add(productDiscountLayout, categoryDiscountLayout, storeDiscountLayout);
        discountGrids.setWidthFull();

        add(
                productAndPolicyGrids,
                new H1("Discount Lists"),
                discountGrids,
                new Button("View Purchases", e -> viewPurchasesDialog()),
                new Button("Close Store", e -> CloseStore())
        );

        setWidthFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private static void successMessage(Dialog dialog, Label errorSuccessLabel, String msg) {
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
        purchaseGrid.addColumn(PurchaseProductDTO::getPrice).setHeader("Price").setSortable(true).setTextAlign(ColumnTextAlign.START).setAutoWidth(true);
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
        productMap = marketController.getProductsByStore(storeId).value;
        productGrid.setItems(productMap.keySet().stream().toList());
        purchasePolicyList = marketController.getPurchasePoliciesByStoreId(storeId).value;
        purchasePolicyGrid.setItems(purchasePolicyList);
        ResponseT<List<MemberDTO>> employeeListResponse = marketController.getStoreEmployees(MainLayout.getSessionId(), storeId);
        employeesList = employeeListResponse.getError_occurred() ? new ArrayList<>() : employeeListResponse.value;
        employeesGrid.setItems(employeesList);
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

    private VerticalLayout initProductGrid() {
        VerticalLayout products = new VerticalLayout();
        HorizontalLayout productsHL = new HorizontalLayout();
        Div productsDiv = new Div();
        boolean hasPermission = marketController.hasPermission(MainLayout.getSessionId(), storeId, PositionDTO.permissionType.Inventory).value;
        Button addProductButton = new Button("+", e -> addProductDialog());
        addProductButton.setEnabled(hasPermission);
        productsHL.add(new H1("Product List"), productsDiv, addProductButton);
        productsHL.setFlexGrow(1, productsDiv);
        productsHL.setWidthFull();
        productGrid = new Grid<>(ProductDTO.class, false);
        productGrid.addColumn(ProductDTO::getProductId).setHeader("Id").setSortable(true).setTextAlign(ColumnTextAlign.START).setKey("Id").setFlexGrow(0);
        productGrid.addColumn(ProductDTO::getProductName).setHeader("Name").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(2);
        productGrid.addColumn(ProductDTO::getCategory).setHeader("Category").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(2);
        productGrid.addColumn(ProductDTO::getPrice).setHeader("Price").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(0);
        productGrid.addColumn(ProductDTO::getRating).setHeader("Rating").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(0);
        productGrid.addColumn(productDTO -> productMap.get(productDTO)).setHeader("Quantity").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(1);
        productGrid.addComponentColumn(productDTO -> {
            if (hasPermission)
                return new Button("Edit", e -> editProductDialog(productDTO));
            return new Div();
        }).setFlexGrow(0).setAutoWidth(true);
        productGrid.addComponentColumn(productDTO -> {
            if (hasPermission)
                return new Button("Remove", e -> removeProductDialog(productDTO.getProductId()));
            return new Div();
        }).setFlexGrow(0).setAutoWidth(true);
        productGrid.sort(List.of(new GridSortOrder<>(productGrid.getColumnByKey("Id"), SortDirection.ASCENDING)));
        products.add(productsHL, productGrid);
        return products;
    }

    private VerticalLayout initPurchasePolicyGrid() {
        VerticalLayout purchasePolicies = new VerticalLayout();
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
        purchasePolicies.add(purchasePoliciesHL, purchasePolicyGrid);
        return purchasePolicies;
    }

    private VerticalLayout initEmployeesGrid() {
        VerticalLayout employees = new VerticalLayout();
        HorizontalLayout employeesHL = new HorizontalLayout();
        Div employeesDiv = new Div();
        Button addEmployeeButton = new Button("+", e -> addEmployeeDialog());
        employeesHL.add(new H1("Employees List"), employeesDiv, addEmployeeButton);
        addEmployeeButton.setEnabled(marketController.hasPermission(MainLayout.getSessionId(), storeId, PositionDTO.permissionType.setNewPosition).value);
        employeesHL.setFlexGrow(1, employeesDiv);
        employeesHL.setWidthFull();
        employeesGrid = new Grid<>(MemberDTO.class, false);
        employeesGrid.addColumn(memberDTO -> employeesList.indexOf(memberDTO) + 1).setHeader("#").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(0);
        employeesGrid.addColumn(MemberDTO::getUsername).setHeader("Name").setSortable(true).setTextAlign(ColumnTextAlign.START);
        employeesGrid.addColumn(memberDTO -> memberDTO.getPositions().stream()
                .filter(position -> position.getStore().getStoreId() == storeId)
                .map(PositionDTO::getPositionName)
                .findFirst().orElse("")).setHeader("Position").setSortable(true).setTextAlign(ColumnTextAlign.START);
        employeesGrid.addComponentColumn(memberDTO -> {
            if (Objects.equals(memberDTO.getUsername(), marketController.getUsername(MainLayout.getSessionId()).value)) {
                Div div = new Div();
                div.getStyle().set("white-space", "pre-wrap");
                div.setText("You");
                return div;
            }
            if (!marketController.hasPermission(MainLayout.getSessionId(), storeId, PositionDTO.permissionType.setPermissions).value)
                return new Div();
            PositionDTO position = memberDTO.getPositions().stream().filter(p -> p.getStore().getStoreId() == storeId).findFirst().orElse(null);
            if (position != null && position.getAssigner().getUsername().equals(marketController.getUsername(MainLayout.getSessionId()).value))
                return switch (position.getPositionName()) {
                    case "Owner" -> new Button("Remove", e -> removeOwnerDialog(memberDTO));
                    case "Manager" -> new Button("Edit", e -> editManagerPermissions(memberDTO));
                    default -> new Div();
                };
            return new Div();
        }).setTextAlign(ColumnTextAlign.CENTER);
        employees.add(employeesHL, employeesGrid);
        return employees;
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

    private void addProductDialog() {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Add New Product");
        Label errorSuccessLabel = new Label();
        TextField productNameField = new TextField();
        NumberField priceField = new NumberField();
        Select<String> categoryField = new Select<>();
        TextField newCategoryField = new TextField();
        IntegerField quantityField = new IntegerField();
        TextArea descriptionField = new TextArea();
        Button submitButton = new Button("Submit", event -> {
            if (priceField.getValue() == null)
                errorSuccessLabel.setText("Price can't be empty");
            else if (quantityField.getValue() == null)
                errorSuccessLabel.setText("Quantity can't be empty");
            else {
                ResponseT<ProductDTO> response = marketController.addProduct(
                        MainLayout.getSessionId(),
                        storeId,
                        productNameField.getValue(),
                        priceField.getValue(),
                        newCategoryField.isVisible() ? newCategoryField.getValue() : categoryField.getValue(),
                        quantityField.getValue(),
                        descriptionField.getValue());
                if (response.getError_occurred())
                    errorSuccessLabel.setText(response.error_message);
                else
                    successMessage(dialog, errorSuccessLabel, "Product added successfully!");
            }
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());

        productNameField.setPlaceholder("Product name");
        priceField.setPlaceholder("Price");
        categoryField.setPlaceholder("Category");
        newCategoryField.setPlaceholder("New category");
        quantityField.setPlaceholder("Quantity");
        descriptionField.setPlaceholder("Description");

        List<String> lst = new ArrayList<>();
        lst.add("new");
        lst.addAll(marketController.getAllCategories().value);
        categoryField.setItems(lst);
        categoryField.addComponents("new", new Hr());
        categoryField.addValueChangeListener(event -> newCategoryField.setVisible(event.getValue().equals("new")));

        newCategoryField.setVisible(false);

        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, productNameField, priceField, categoryField, newCategoryField, quantityField, descriptionField, submitButton, cancelButton);
        dialog.add(vl);
        dialog.open();
    }

    private void editProductDialog(ProductDTO product) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Edit product details");
        Label errorSuccessLabel = new Label();
        Select<String> productFieldSelect = new Select<>();
        productFieldSelect.setItems("Product Name", "Category", "Price", "Description");
        productFieldSelect.setPlaceholder("Field to change");
        productFieldSelect.setItemEnabledProvider(
                item -> !"Description".equals(item));
        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, productFieldSelect);

        TextField productNameField = new TextField();
        Select<String> categoryField = new Select<>();
        TextField newCategoryField = new TextField();
        NumberField priceField = new NumberField();
        TextArea descriptionField = new TextArea();

        productNameField.setPlaceholder("Product name");
        priceField.setPlaceholder("Price");
        categoryField.setPlaceholder("Category");
        newCategoryField.setPlaceholder("New category");
        descriptionField.setPlaceholder("Description");

        List<String> lst = new ArrayList<>();
        lst.add("new");
        lst.addAll(marketController.getAllCategories().value);
        categoryField.setItems(lst);
        categoryField.addComponents("new", new Hr());
        categoryField.addValueChangeListener(event -> newCategoryField.setVisible(event.getValue().equals("new")));

        List<Component> components = new ArrayList<>();
        components.add(productNameField);
        components.add(priceField);
        components.add(categoryField);
        components.add(newCategoryField);
        components.add(descriptionField);
        components.forEach(component -> component.setVisible(false));

        categoryField.setValue(product.getCategory());

        newCategoryField.setVisible(false);

        Button submitButton = new Button("Submit");
        submitButton.setEnabled(false);
        final Registration[] clickListener = new Registration[1];
        productFieldSelect.addValueChangeListener(e -> {
            submitButton.setEnabled(true);
            switch (e.getValue()) {
                case "Product Name" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    productNameField.setVisible(true);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.editProductName(
                                MainLayout.getSessionId(),
                                storeId,
                                product.getProductId(),
                                productNameField.getValue());
                        if (response.getError_occurred())
                            errorSuccessLabel.setText(response.error_message);
                        else
                            successMessage(dialog, errorSuccessLabel, "Name changed successfully");
                    });
                }
                case "Category" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    categoryField.setVisible(true);
                    newCategoryField.setVisible(true);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.editProductCategory(
                                MainLayout.getSessionId(),
                                storeId,
                                product.getProductId(),
                                Objects.equals(categoryField.getValue(), "new") ? newCategoryField.getValue() : categoryField.getValue());
                        if (response.getError_occurred())
                            errorSuccessLabel.setText(response.error_message);
                        else
                            successMessage(dialog, errorSuccessLabel, "Category changed successfully");
                    });
                }
                case "Price" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    priceField.setVisible(true);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        if (priceField.getValue() == null) {
                            errorSuccessLabel.setText("Price can't be empty");
                            return;
                        }
                        Response response = marketController.editProductPrice(
                                MainLayout.getSessionId(),
                                storeId,
                                product.getProductId(),
                                priceField.getValue());
                        if (response.getError_occurred())
                            errorSuccessLabel.setText(response.error_message);
                        else
                            successMessage(dialog, errorSuccessLabel, "Price changed successfully");
                    });
                }
                case "Description" -> {
                    if (clickListener[0] != null)
                        clickListener[0].remove();
                    components.forEach(component -> component.setVisible(false));
                    descriptionField.setVisible(true);
//                    clickListener[0] = submitButton.addClickListener(event -> {
//                        Response response = marketController.editProductDescription(
//                                MainLayout.getSessionId(),
//                                storeId,
//                                productId,
//                                descriptionField.getValue());
//                        if (response.getError_occurred())
//                            errorSuccessLabel.setText(response.error_message);
//                        else
//                            successMessage(dialog, errorSuccessLabel, "Description changed successfully");
//                    });
                }
                default -> {
                }
            }
        });
        vl.add(productNameField, categoryField, newCategoryField, priceField, descriptionField, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();
    }

    private void removeProductDialog(int productId) {
        Dialog dialog = new Dialog();
        VerticalLayout vl = new VerticalLayout();
        Label errorSuccessLabel = new Label();
        Label label = new Label("Are you sure? This cannot be undone.");
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(
                new Button("Remove", e -> {
                    Response response = marketController.removeProductFromStore(MainLayout.getSessionId(), storeId, productId);
                    if (response.getError_occurred()) {
                        errorSuccessLabel.setText(response.error_message);
                    } else
                        successMessage(dialog, errorSuccessLabel, "Policy removed successfully");
                }),
                new Button("Cancel", e -> dialog.close())
        );
        vl.add(errorSuccessLabel, label, hl);
        dialog.add(vl);
        dialog.open();
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
                            successMessage(dialog, errorSuccessLabel, "Policy added successfully");
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
                            successMessage(dialog, errorSuccessLabel, "Policy added successfully");
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
                            successMessage(dialog, errorSuccessLabel, "Policy added successfully");
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
                            successMessage(dialog, errorSuccessLabel, "Policy added successfully");
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
                successMessage(dialog, errorSuccessLabel, "Policies joined successfully");
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
                        successMessage(dialog, errorSuccessLabel, "Policy removed successfully");
                }),
                new Button("Cancel", e -> dialog.close())
        );
        vl.add(errorSuccessLabel, label, hl);
        dialog.add(vl);
        dialog.open();
    }

    private void addEmployeeDialog() {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Add New Employee");
        Label errorSuccessLabel = new Label();
        TextField usernameField = new TextField();
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroup.setLabel("Type");
        radioGroup.setItems("Manager", "Owner");

        Button cancelButton = new Button("Cancel", event -> dialog.close());
        usernameField.setPlaceholder("Username");

        Button submitButton = new Button("Submit", event -> {
            switch (radioGroup.getValue()) {
                case "Manager" -> {
                    Response response = marketController.setPositionOfMemberToStoreManager(
                            MainLayout.getSessionId(),
                            storeId,
                            usernameField.getValue());
                    if (response.getError_occurred())
                        errorSuccessLabel.setText(response.error_message);
                    else
                        successMessage(dialog, errorSuccessLabel, "Manager added successfully!");
                }
                case "Owner" -> {
                    Response response = marketController.setPositionOfMemberToStoreOwner(
                            MainLayout.getSessionId(),
                            storeId,
                            usernameField.getValue());
                    if (response.getError_occurred())
                        errorSuccessLabel.setText(response.error_message);
                    else
                        successMessage(dialog, errorSuccessLabel, "Manager added successfully!");
                }
            }
        });

        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, usernameField, radioGroup, submitButton, cancelButton);
        dialog.add(vl);
        dialog.open();
    }

    private void removeOwnerDialog(MemberDTO memberDTO) {
        Dialog dialog = new Dialog();
        VerticalLayout vl = new VerticalLayout();
        Label errorSuccessLabel = new Label();
        Label label = new Label("Are you sure? This cannot be undone.");
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(
                new Button("Remove", e -> {
                    Response response = marketController.removeStoreOwner(MainLayout.getSessionId(), storeId, memberDTO.getUsername());
                    if (response.getError_occurred())
                        errorSuccessLabel.setText(response.error_message);
                    else
                        successMessage(dialog, errorSuccessLabel, "Owner removed successfully");
                }),
                new Button("Cancel", e -> dialog.close())
        );
        vl.add(errorSuccessLabel, label, hl);
        dialog.add(vl);
        dialog.open();
    }

    private void editManagerPermissions(MemberDTO employee) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        Label errorSuccessLabel = new Label();
        header.setText("Edit Manager Permissions");
        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Export data");
        checkboxGroup.setItems(PositionDTO.stringToPermMap.keySet());
        checkboxGroup.select("Order ID", "Customer");
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        ResponseT<Set<PositionDTO.permissionType>> permissions = marketController.getPermissions(MainLayout.getSessionId(), storeId, employee.getUsername());
        Set<String> stringPermissions = new HashSet<>();
        if (!permissions.getError_occurred())
            stringPermissions = PositionDTO.mapStrings(permissions.value);
        checkboxGroup.setValue(stringPermissions);

        Button submitButton = new Button("Submit", event -> {
            Response response = marketController.setStoreManagerPermissions(
                    MainLayout.getSessionId(),
                    storeId,
                    employee.getUsername(),
                    PositionDTO.mapPermissions(checkboxGroup.getSelectedItems()));
            if (response.getError_occurred())
                errorSuccessLabel.setText(response.error_message);
            else
                successMessage(dialog, errorSuccessLabel, "Permissions set successfully!");
        });


        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, checkboxGroup, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();
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
                    successMessage(dialog, errorSuccessLabel, "Discount added successfully!");
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
                    successMessage(dialog, errorSuccessLabel, "Discount added successfully!");
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
                    successMessage(dialog, errorSuccessLabel, "Discount added successfully!");
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
                            successMessage(dialog2, errorSuccessLabel, "Discount removed successfully");
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
                            successMessage(dialog, errorSuccessLabel, "Policy added successfully");
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
                            successMessage(dialog, errorSuccessLabel, "Policy added successfully");
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
                            successMessage(dialog, errorSuccessLabel, "Policy added successfully");
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
                successMessage(dialog, errorSuccessLabel, "Policies joined successfully");
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
                successMessage(dialog, errorSuccessLabel, "Policy removed successfully");
        });
        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, policySelect, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();

    }

    private String getProductName(ProductDiscountDTO productDiscountDTO) {
        return Objects.requireNonNull(productMap.keySet().stream().filter(productDTO -> productDTO.getProductId() == productDiscountDTO.getProductId()).findFirst().orElse(null)).getProductName();
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
