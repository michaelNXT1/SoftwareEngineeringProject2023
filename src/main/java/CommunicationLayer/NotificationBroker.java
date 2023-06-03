package CommunicationLayer;

import BusinessLayer.Notification.Notification;
public interface NotificationBroker {

    /***
     * this function must support thread safe!
     * @param notification
     */
    void sendNotificationToUser(Notification notification, String ... memberName);


}
