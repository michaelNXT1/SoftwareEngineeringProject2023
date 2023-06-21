package application.views;

import CommunicationLayer.MarketController;
import ServiceLayer.DTOs.MemberDTO;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//@PageTitle("GetInformationAboutMember ")
//@Route(value = "getInformationAboutMember ")
public class GetInformationAboutMembers extends Dialog {
    private MarketController marketController;
    private Grid<MemberDTO> grid;

//    @Autowired
    public GetInformationAboutMembers() {
        VerticalLayout vl=new VerticalLayout();
        this.marketController = MarketController.getInstance();

//        // Create grid for member data
        grid = new Grid<>(MemberDTO.class);
        grid.setColumns("username", "positions"); // Customize the columns as per your MemberDTO

        // Load member data into grid
        refreshData();

        // Add grid to the layout
        vl.add(grid);
        vl.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        vl.getStyle().set("text-align", "center");
        setWidth("50%");
        setHeight("50%");
        add(vl);
    }

    private void refreshData() {
        ResponseT<List<MemberDTO>> r= marketController.getInformationAboutMembers(MainLayout.getSessionId());
        if (r.getError_occurred())
            Notification.show(r.error_message, 3000, Notification.Position.MIDDLE);
        else {
            Notification.show("you get Information About Members", 3000, Notification.Position.MIDDLE);
            List<MemberDTO> members =r.value;
            grid.setItems(members);
        }
    }
}
