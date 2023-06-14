package application.views.helloworld;

import CommunicationLayer.IMarketController;
import CommunicationLayer.MarketController;
import CommunicationLayer.NotificationController;
import application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@PreserveOnRefresh
public class HelloWorldView extends HorizontalLayout {


    private final TextField name;

    public HelloWorldView() {
        IMarketController marketController = MarketController.getInstance();
        name = new TextField("Your name");
        Button sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> Notification.show("Hello " + name.getValue()));
        sayHello.addClickShortcut(Key.ENTER);

        Button openDialogButton = new Button("Open Dialog");
        Dialog dialog = new Dialog();
        dialog.add(new Text("This is a dialog"));
        Button closeDialogButton = new Button("Close");
        closeDialogButton.addClickListener(event -> dialog.close());
        dialog.add(closeDialogButton);

        openDialogButton.addClickListener(event -> dialog.open());

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        add(openDialogButton);
        add(new H1("Welcome, " + marketController.getUsername(MainLayout.getSessionId()).value + "!"));
    }

}
