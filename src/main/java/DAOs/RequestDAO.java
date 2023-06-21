package DAOs;

import BusinessLayer.EmployeeRequest;
import Repositories.IRequestRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RequestDAO implements IRequestRepository {
    @Override
    public void updateRequest(EmployeeRequest offer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(offer);
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
    public void saveRequest(EmployeeRequest offer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(offer);
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
    public void deleteRequest(EmployeeRequest offer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(offer);
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
    public List<EmployeeRequest> getAllRequests() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<EmployeeRequest> offerList = null;
        try {
            offerList = session.createQuery("FROM EmployeeRequest", EmployeeRequest.class).list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return offerList;
    }

    @Override
    public void addAllRequests(List<EmployeeRequest> offerList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (EmployeeRequest offer : offerList) {
                session.persist(offer);
            }
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
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM EmployeeRequest").executeUpdate();
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
