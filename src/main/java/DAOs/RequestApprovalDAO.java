package DAOs;

import BusinessLayer.RequestApproval;
import Repositories.IRequestApprovalRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RequestApprovalDAO implements IRequestApprovalRepository {
    @Override
    public void updateRequestApproval(RequestApproval requestApproval) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(requestApproval);
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
    public void saveRequestApproval(RequestApproval requestApproval) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(requestApproval);
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
    public void deleteRequestApproval(RequestApproval requestApproval) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(requestApproval);
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
    public List<RequestApproval> getAllRequestApprovals() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<RequestApproval> requestApprovalList = null;
        try {
            requestApprovalList = session.createQuery("FROM RequestApproval", RequestApproval.class).list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return requestApprovalList;
    }

    @Override
    public void addAllRequestApprovals(List<RequestApproval> requestApprovalList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (RequestApproval requestApproval : requestApprovalList) {
                session.persist(requestApproval);
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
            session.createQuery("DELETE FROM RequestApproval").executeUpdate();
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
