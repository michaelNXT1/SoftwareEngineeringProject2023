package application.views.StoreManagement;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.EmployeeRequestDTO;
import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.Response;
import application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class EmployeeRequestManagementView extends VerticalLayout {
    private final int storeId;
    private final Grid<EmployeeRequestDTO> requestGrid;
    private final MarketController marketController;

    public EmployeeRequestManagementView(MarketController marketController, int storeId){
        this.storeId = storeId;
        this.marketController = marketController;
        HorizontalLayout hl = new HorizontalLayout();
        Div productsDiv = new Div();
        hl.add(new H1("Employee Request List"), productsDiv);
        hl.setFlexGrow(1, productsDiv);
        hl.setWidthFull();
        requestGrid = new Grid<>(EmployeeRequestDTO.class, false);
        requestGrid.addColumn(employeeRequestDTO -> employeeRequestDTO.getCandidate().getUsername()).setHeader("Candidate").setSortable(true).setTextAlign(ColumnTextAlign.START);
        requestGrid.addColumn(employeeRequestDTO -> employeeRequestDTO.getRequestingUser().getUsername()).setHeader("Requested by").setSortable(true).setTextAlign(ColumnTextAlign.START);
        requestGrid.addComponentColumn(employeeRequestDTO -> {
            String username = marketController.getUsername(MainLayout.getSessionId()).value;
            MemberDTO member = employeeRequestDTO.getApprovingMembers().keySet().stream().filter(memberDTO -> memberDTO.getUsername().equals(username)).findFirst().orElse(null);
            int offerResponse = employeeRequestDTO.getApprovingMembers().get(member);
            HorizontalLayout buttonHl = new HorizontalLayout();
            switch (offerResponse) {
                case -1 -> {
                    Button acceptButton = new Button("Accept", e -> acceptRequest(employeeRequestDTO));
                    Button rejectButton = new Button("Reject", e -> rejectRequest(employeeRequestDTO));
                    buttonHl.add(acceptButton, rejectButton);
                }
                case 0 -> buttonHl.add(new Label("You have rejected this offer"));
                case 1 -> buttonHl.add(new Label("You have accepted this offer"));
            }
            return buttonHl;
        });
        add(hl, requestGrid);
    }

    private void rejectRequest(EmployeeRequestDTO requestDTO) {
        Response response = marketController.rejectRequest(MainLayout.getSessionId(), storeId, requestDTO.getRequestId());
        if (response.getError_occurred())
            Notification.show(response.error_message);
        else
            Notification.show("Request rejected successfully");
    }

    private void acceptRequest(EmployeeRequestDTO requestDTO) {
        Response response = marketController.acceptRequest(MainLayout.getSessionId(), storeId, requestDTO.getRequestId());
        if (response.getError_occurred())
            Notification.show(response.error_message);
        else
            Notification.show("Request accepted successfully");
    }

    public void setRequestGrid(List<EmployeeRequestDTO> requestDTOList) {
        requestGrid.setItems(requestDTOList);
    }
}
