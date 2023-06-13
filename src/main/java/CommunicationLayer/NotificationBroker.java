package CommunicationLayer;

import Notification.Notification;
public interface NotificationBroker {

    /***
     * this function must support thread safe!
     * @param notification
     */
    void sendRealTimeNotification(Notification notification, String ... memberName);

    void sendDataUpdateNotice(String... adminUserName);

}
