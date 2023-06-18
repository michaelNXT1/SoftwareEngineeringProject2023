package application.views.about;

import application.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PreserveOnRefresh
public class AboutView extends VerticalLayout {


    public AboutView() {
        setSpacing(false);

//        Image img = new Image("images/empty-plant.png", "placeholder plant");
//        img.setWidth("200px");
//        add(img);

        H2 header = new H2("meet our company");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        HorizontalLayout row1 = new HorizontalLayout();
        row1.setWidthFull();
        row1.setJustifyContentMode(JustifyContentMode.CENTER);

        Image womanImage1 = new Image("images/Shoham.png", "Woman 1");
        womanImage1.setWidth("200px");
        VerticalLayout vlShoham=new VerticalLayout(womanImage1,new Paragraph("Shoham  -CEO"));
        vlShoham.setWidth("200px");
        vlShoham.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        Image womanImage2 = new Image("images/Shani.png", "Woman 2");
        womanImage2.setWidth("200px");
        VerticalLayout vlShani=new VerticalLayout(womanImage2,new Paragraph("Shani  -CEO"));
        vlShani.setWidth("200px");
        vlShani.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        Image manImage1 = new Image("images/Michael.png", "Man 1");
        manImage1.setWidth("200px");
        VerticalLayout vlMichael=new VerticalLayout(manImage1,new Paragraph("Michael  -CEO"));
        vlMichael.setWidth("200px");
        vlMichael.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        row1.add(vlShoham, vlShani, vlMichael);
        add(row1);

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setJustifyContentMode(JustifyContentMode.CENTER);

        Image manImage2 = new Image("images/Alon.png", "Man 2");
        manImage2.setWidth("200px");
        VerticalLayout vlAlon=new VerticalLayout(manImage2,new Paragraph("Alon  -CEO"));
        vlAlon.setWidth("200px");
        vlAlon.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        Image manImage3 = new Image("images/Idan.png", "Man 3");
        manImage3.setWidth("200px");
        VerticalLayout vlIdan=new VerticalLayout(manImage3,new Paragraph("Idan  -CEO"));
        vlIdan.setWidth("200px");
        vlIdan.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        row2.add(vlAlon, vlIdan);
        add(row2);

        add(new Paragraph("our rockstar 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
