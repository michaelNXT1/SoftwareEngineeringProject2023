package Repositories;

import Notification.Notification;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface INotificationRepository {
    void addNotification(Notification notification);
    void removeNotification(Notification notification);
    void clear();
    ConcurrentLinkedQueue<Notification> getAllNotifications();
}
