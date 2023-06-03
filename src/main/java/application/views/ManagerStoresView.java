package application.views;

import CommunicationLayer.MarketController;
import CommunicationLayer.NotificationController;
import ServiceLayer.DTOs.StoreDTO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = "ManagerStoresView", layout = MainLayout.class)
@PreserveOnRefresh
public class ManagerStoresView extends VerticalLayout {

    @Autowired
    public ManagerStoresView() throws Exception {
        MarketController marketController = MarketController.getInstance();
        List<StoreDTO> stores = marketController.getResponsibleStores(MainLayout.getSessionId()).value;
        Header header = new Header();
        header.setText("Stores under " + marketController.getUsername(MainLayout.getSessionId()).value);
        add(header);
        Grid<StoreDTO> grid = new Grid<>(StoreDTO.class, false);
        grid.addColumn(storeDTO -> stores.indexOf(storeDTO) + 1).setHeader("#").setSortable(true).setTextAlign(ColumnTextAlign.START);
        grid.addColumn(StoreDTO::getStoreName).setHeader("Store Name").setSortable(true).setTextAlign(ColumnTextAlign.START);
        grid.addColumn(s -> s.isOpen() ? "Open" : "Closed").setHeader("Status").setSortable(true).setTextAlign(ColumnTextAlign.START);
        grid.addComponentColumn(store -> new Button("Manage", e -> UI.getCurrent().navigate("StoreManagementView/" + store.getStoreId())));
        grid.setItems(stores);
        grid.setVisible(!stores.isEmpty());
        add(grid);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
