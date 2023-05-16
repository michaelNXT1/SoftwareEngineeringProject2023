package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.ProductDTO;
import ServiceLayer.DTOs.StoreDTO;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.button.Button;

import java.util.List;


@Route(value = "ManagerStoresView", layout = MainLayout.class)
public class ManagerStoresView extends VerticalLayout {
    private MarketController marketController;
    private Header header;

    @Autowired
    public ManagerStoresView() {
        this.marketController = MarketController.getInstance();
        List<StoreDTO> stores = marketController.getResponsibleStores(MainLayout.getSessionId());
        this.header = new Header();
        this.header.setText("Stores under " + marketController.getUsername(MainLayout.getSessionId()));
        add(header);
        Grid<StoreDTO> grid = new Grid<>(StoreDTO.class, false);
        grid.addColumn(storeDTO -> stores.indexOf(storeDTO) + 1).setHeader("#").setSortable(true).setTextAlign(ColumnTextAlign.START);
        grid.addColumn(StoreDTO::getStoreName).setHeader("Store Name").setSortable(true).setTextAlign(ColumnTextAlign.START);
        grid.addColumn(this::getStoreStatus).setHeader("Status").setSortable(true).setTextAlign(ColumnTextAlign.START);
        grid.addComponentColumn(store -> {
            Button enterButton = new Button("Manage", e -> {
            });
            return enterButton;
        });
        grid.setItems(stores);
        grid.setVisible(!stores.isEmpty());
        add(grid);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

    }

    public String getStoreStatus(StoreDTO s) {
        return s.isOpen() ? "Open" : "Closed";
    }

    private String getRowIndex() {
        int rowIndex = 0;
        return String.valueOf(++rowIndex); // increments the rowIndex for each row
    }
}
