package application.views.StoreManagement;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.DTOs.PositionDTO;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class EmployeeManagementView extends VerticalLayout {
    private final int storeId;
    private final Grid<MemberDTO> employeesGrid;
    private final MarketController marketController;
    private List<MemberDTO> employeesList;

    public EmployeeManagementView(MarketController marketController, int storeId) {
        this.storeId = storeId;
        this.marketController = marketController;
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
        add(employeesHL, employeesGrid);
    }

    public void setEmployeesList(List<MemberDTO> employeesList) {
        this.employeesList = employeesList;
        employeesGrid.setItems(employeesList);
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
                        StoreManagementView.successMessage(dialog, errorSuccessLabel, "Manager added successfully!");
                }
                case "Owner" -> {
                    Response response = marketController.setPositionOfMemberToStoreOwner(
                            MainLayout.getSessionId(),
                            storeId,
                            usernameField.getValue());
                    if (response.getError_occurred())
                        errorSuccessLabel.setText(response.error_message);
                    else
                        StoreManagementView.successMessage(dialog, errorSuccessLabel, "Manager added successfully!");
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
                        StoreManagementView.successMessage(dialog, errorSuccessLabel, "Owner removed successfully");
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
                StoreManagementView.successMessage(dialog, errorSuccessLabel, "Permissions set successfully!");
        });


        VerticalLayout vl = new VerticalLayout();
        vl.add(header, errorSuccessLabel, checkboxGroup, submitButton);
        dialog.add(vl);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        dialog.open();
    }
}
