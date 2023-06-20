package application.views.StoreManagement;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.PositionDTO;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductManagementView extends VerticalLayout {
    private final int storeId;
    private final Grid<ProductDTO> productGrid;
    private final MarketController marketController;

    public ProductManagementView(MarketController marketController, int storeId) {
        this.storeId = storeId;
        this.marketController = marketController;
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
        productGrid.addColumn(productDTO -> productDTO.getPrice() + "§").setHeader("Price").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(0);
        productGrid.addColumn(ProductDTO::getRating).setHeader("Rating").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(0);
        productGrid.addColumn(ProductDTO::getAmount).setHeader("Quantity").setSortable(true).setTextAlign(ColumnTextAlign.START).setFlexGrow(1);
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
        add(productsHL, productGrid);
    }

    public void setProductGrid(List<ProductDTO> productDTOList) {
        productGrid.setItems(productDTOList);
    }

    private void addProductDialog() {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Add New Product");
        Label errorSuccessLabel = new Label();

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

        List<String> categoryList = new ArrayList<>();
        categoryList.add("new");
        categoryList.addAll(marketController.getAllCategories().value);
        categoryField.setItems(categoryList);
        categoryField.addComponents("new", new Hr());
        categoryField.addValueChangeListener(event -> newCategoryField.setVisible(event.getValue().equals("new")));

        newCategoryField.setVisible(false);

        IntegerField quantityField = new IntegerField();
        quantityField.setPlaceholder("Quantity");

        DatePicker datePicker = new DatePicker();
        datePicker.setLabel("Auction end date and time");
        datePicker.setVisible(false);
        TimePicker timePicker = new TimePicker();
        timePicker.setVisible(false);

        Select<String> purchaseTypeField = new Select<>();
        purchaseTypeField.setItems(ProductDTO.stringToPermMap.keySet());
        purchaseTypeField.setPlaceholder("Purchase Type");
        purchaseTypeField.addValueChangeListener(event -> {
            datePicker.setVisible(event.getValue().equals("Auction"));
            timePicker.setVisible(event.getValue().equals("Auction"));
            quantityField.setEnabled(!event.getValue().equals("Auction"));
            quantityField.setValue(event.getValue().equals("Auction") ? 1 : quantityField.getValue());
            quantityField.setLabel(event.getValue().equals("Auction") ? "Auction allows only 1" : "");
        });

        Button submitButton = new Button("Submit", event -> {
            if (priceField.getValue() == null)
                errorSuccessLabel.setText("Price can't be empty");
            else if (quantityField.getValue() == null)
                errorSuccessLabel.setText("Quantity can't be empty");
            else if (purchaseTypeField.getValue() == null)
                errorSuccessLabel.setText("Purchase type can't be empty");
            else if (purchaseTypeField.getValue().equals("Auction")) {
                if (datePicker.getValue() == null)
                    errorSuccessLabel.setText("Date can't be empty");
                if (timePicker.getValue() == null)
                    errorSuccessLabel.setText("Time can't be empty");
                if (LocalDateTime.of(datePicker.getValue(), timePicker.getValue()).isBefore(LocalDateTime.now()))
                    errorSuccessLabel.setText("Auction end time cannot be before now");
                else {
                    ResponseT<ProductDTO> response = marketController.addAuctionProduct(
                            MainLayout.getSessionId(),
                            storeId,
                            productNameField.getValue(),
                            priceField.getValue(),
                            newCategoryField.isVisible() ? newCategoryField.getValue() : categoryField.getValue(),
                            quantityField.getValue(),
                            descriptionField.getValue(),
                            LocalDateTime.of(datePicker.getValue(), timePicker.getValue())
                    );
                    handleResponse(response, errorSuccessLabel, dialog, "Product added successfully!");
                }
            } else {
                ResponseT<ProductDTO> response = marketController.addProduct(
                        MainLayout.getSessionId(),
                        storeId,
                        productNameField.getValue(),
                        priceField.getValue(),
                        newCategoryField.isVisible() ? newCategoryField.getValue() : categoryField.getValue(),
                        quantityField.getValue(),
                        descriptionField.getValue(),
                        ProductDTO.stringToPermMap.get(purchaseTypeField.getValue()));
                handleResponse(response, errorSuccessLabel, dialog, "Product added successfully!");
            }
        });


        VerticalLayout vl = new VerticalLayout(header, errorSuccessLabel, productNameField, priceField, categoryField, newCategoryField, quantityField, descriptionField, purchaseTypeField, datePicker, timePicker, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();
    }

    private void editProductDialog(ProductDTO product) {
        Dialog dialog = new Dialog();
        Header header = new Header();
        header.setText("Edit product details");
        Label errorSuccessLabel = new Label();

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

        newCategoryField.setVisible(false);

        Select<String> productFieldSelect = new Select<>();
        productFieldSelect.setItems("Product Name", "Category", "Price", "Description");
        productFieldSelect.setPlaceholder("Field to change");
        productFieldSelect.setItemEnabledProvider(
                item -> !"Description".equals(item));

        List<Component> components = new ArrayList<>();
        components.add(productNameField);
        components.add(priceField);
        components.add(categoryField);
        components.add(newCategoryField);
        components.add(descriptionField);
        components.forEach(component -> component.setVisible(false));

        categoryField.setValue(product.getCategory());

        Button submitButton = new Button("Submit");
        submitButton.setEnabled(false);
        final Registration[] clickListener = new Registration[1];
        productFieldSelect.addValueChangeListener(e -> {
            submitButton.setEnabled(true);
            switch (e.getValue()) {
                case "Product Name" -> {
                    handleFieldSelection(productNameField, components, clickListener);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.editProductName(MainLayout.getSessionId(), storeId, product.getProductId(), productNameField.getValue());
                        handleResponse(response, errorSuccessLabel, dialog, "Name changed successfully");
                    });
                }
                case "Category" -> {
                    handleFieldSelection(categoryField, components, clickListener);
                    newCategoryField.setVisible(true);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.editProductCategory(MainLayout.getSessionId(), storeId, product.getProductId(), Objects.equals(categoryField.getValue(), "new") ? newCategoryField.getValue() : categoryField.getValue());
                        handleResponse(response, errorSuccessLabel, dialog, "Category changed successfully");
                    });
                }
                case "Price" -> {
                    handleFieldSelection(priceField, components, clickListener);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        if (priceField.getValue() == null) {
                            errorSuccessLabel.setText("Price can't be empty");
                            return;
                        }
                        Response response = marketController.editProductPrice(MainLayout.getSessionId(), storeId, product.getProductId(), priceField.getValue());
                        handleResponse(response, errorSuccessLabel, dialog, "Price changed successfully");
                    });
                }
                case "Description" -> {
                    handleFieldSelection(descriptionField, components, clickListener);
                    clickListener[0] = submitButton.addClickListener(event -> {
                        Response response = marketController.editProductDescription(MainLayout.getSessionId(), storeId, product.getProductId(), descriptionField.getValue());
                        handleResponse(response, errorSuccessLabel, dialog, "Description changed successfully");
                    });
                }
            }
        });
        VerticalLayout vl = new VerticalLayout(header, errorSuccessLabel, productFieldSelect, productNameField, categoryField, newCategoryField, priceField, descriptionField, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();
    }

    private void handleFieldSelection(Component field, List<Component> components, Registration[] clickListener) {
        if (clickListener[0] != null)
            clickListener[0].remove();
        components.forEach(component -> component.setVisible(false));
        field.setVisible(true);
    }

    private void handleResponse(Response response, Label errorSuccessLabel, Dialog dialog, String Name_changed_successfully) {
        if (response.getError_occurred())
            errorSuccessLabel.setText(response.error_message);
        else
            StoreManagementView.successMessage(dialog, errorSuccessLabel, Name_changed_successfully);
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
                        StoreManagementView.successMessage(dialog, errorSuccessLabel, "Policy removed successfully");
                }),
                new Button("Cancel", e -> dialog.close())
        );
        vl.add(errorSuccessLabel, label, hl);
        dialog.add(vl);
        dialog.open();
    }
}
