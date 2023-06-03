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

        Image womanImage1 = new Image("images/woman2.png", "Woman 1");
        womanImage1.setWidth("200px");
        Paragraph womanCaption1 = new Paragraph("SHOHAN  -CEO");

        Image womanImage2 = new Image("images/woman2.png", "Woman 2");
        womanImage2.setWidth("200px");
        Paragraph womanCaption2 = new Paragraph("SHANI  -CEO");

        Image manImage1 = new Image("images/man2.png", "Man 1");
        manImage1.setWidth("200px");
        Paragraph manCaption1 = new Paragraph("MICHAEL  -CEO");

        row1.add(womanImage1, womanImage2, manImage1);
        add(row1);
        HorizontalLayout row11 = new HorizontalLayout();
        row11.setWidthFull();
        row11.setJustifyContentMode(JustifyContentMode.CENTER);
        row11.add( womanCaption1, womanCaption2, manCaption1);
        add(row11);

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setJustifyContentMode(JustifyContentMode.CENTER);

        Image manImage2 = new Image("images/man2.png", "Man 2");
        manImage2.setWidth("200px");
        Paragraph manCaption2 = new Paragraph("ALON  -CEO");

        Image manImage3 = new Image("images/man2.png", "Man 3");
        manImage3.setWidth("200px");
        Paragraph manCaption3 = new Paragraph("IDAN   -CEO");

        row2.add(manImage2, manImage3);
        add(row2);

        HorizontalLayout row22 = new HorizontalLayout();
        row22.setWidthFull();
        row22.setJustifyContentMode(JustifyContentMode.CENTER);
        row22.add( manCaption2, manCaption3);
        add(row22);

        add(new Paragraph("our rockstar 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
