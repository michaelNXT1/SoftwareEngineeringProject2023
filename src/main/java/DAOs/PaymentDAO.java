package DAOs;

import BusinessLayer.PaymentDetails;
import Repositories.IPaymantRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PaymentDAO implements IPaymantRepo {
    @Override
    public void addPayment(PaymentDetails paymentDetails) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(paymentDetails);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void removePayment(PaymentDetails paymentDetails) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(paymentDetails);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public PaymentDetails getPayment(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        PaymentDetails paymentDetails = null;
        try {
            paymentDetails = session.get(PaymentDetails.class, key);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return paymentDetails;
    }

    @Override
    public List<PaymentDetails> getAllPayment() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PaymentDetails> paymentDetails = null;
        try {
            String hql = "FROM PaymentDetails";
            Query<PaymentDetails> query = session.createQuery(hql, PaymentDetails.class);
            paymentDetails = query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return paymentDetails;
    }

    @Override
    public void updatePayment(PaymentDetails paymentDetails) {

    }

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM PaymentDetails").executeUpdate();
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
