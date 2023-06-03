package BusinessLayer.Repositories;

import BusinessLayer.Notification.Notification;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface INotificationRepository {
    void addNotification(Notification notification);
    void removeNotification(Notification notification);
    ConcurrentLinkedQueue<Notification> getAllNotifications();
    void clear();
}
