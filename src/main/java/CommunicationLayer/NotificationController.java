package CommunicationLayer;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import Notification.Notification;

@Controller
public class NotificationController implements NotificationBroker {

    @Override
    public void sendNotificationToUser(Notification notification, String ... memberName) {
        UI ui = UI.getCurrent();
        ui.access(() -> {
            com.vaadin.flow.component.notification.Notification.show(notification.getMessage());
        });
        System.out.println(String.format("sent to %s the massage was %s",memberName[0],notification.getMessage()));
    }
}



