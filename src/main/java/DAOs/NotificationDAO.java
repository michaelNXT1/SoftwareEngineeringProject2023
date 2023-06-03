package DAOs;

import BusinessLayer.Repositories.INotificationRepository;
import BusinessLayer.Notification.Notification;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NotificationDAO implements INotificationRepository {
    private final ConcurrentLinkedQueue<Notification> notifications;

    public NotificationDAO() {
        this.notifications = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void addNotification(Notification notification) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(notification);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            notifications.add(notification);
        }
    }

    @Override
    public void removeNotification(Notification notification) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(notification);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            notifications.remove(notification);
        }
    }

    @Override
    public ConcurrentLinkedQueue<Notification> getAllNotifications() {
        ConcurrentLinkedQueue<Notification> notifications = new ConcurrentLinkedQueue<>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            notifications.addAll(session.createQuery("FROM Notification", Notification.class).list());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return notifications;
    }

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Notification").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
