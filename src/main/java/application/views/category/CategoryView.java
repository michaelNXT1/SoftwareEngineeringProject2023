package application.views.category;

import application.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;


@Route(value = "category", layout = MainLayout.class)
@PreserveOnRefresh
public class CategoryView extends HorizontalLayout {
}
