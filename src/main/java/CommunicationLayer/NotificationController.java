package CommunicationLayer;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import Notification.Notification;

@Controller
public class NotificationController implements NotificationBroker {

    private VaadinSession session = VaadinSession.getCurrent();
    private UI ui = UI.getCurrent();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendRealTimeNotification(@Payload Notification notification, String ... memberName) {
        System.out.println(String.format("sent to %s the massage was %s",memberName[0],notification.getMessage()));
//        try {
//            simpMessagingTemplate.convertAndSend("/user/queue/specific/" + memberName[0], notification.getMessage() + "\nSent at: " + notification.getCreatedAt().toString());
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
    }

    @Override
    public void sendDataUpdateNotice(String... adminUserName) {
//        System.out.println("sent update to "+adminUserName[0]);
//        simpMessagingTemplate.convertAndSend("/user/queue/admins/"+adminUserName[0], "An update to daily data has occurred");
    }
}



