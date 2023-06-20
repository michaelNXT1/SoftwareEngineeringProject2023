package DAOs;

import BusinessLayer.OfferApproval;

import Repositories.IOfferApprovalRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OfferApprovalDAO implements IOfferApprovalRepository {
    @Override
    public void updateOfferApproval(OfferApproval offerApproval) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(offerApproval);
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
    public void saveOfferApproval(OfferApproval offerApproval) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(offerApproval);
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
    public void deleteOfferApproval(OfferApproval offerApproval) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(offerApproval);
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
    public List<OfferApproval> getAllOfferApprovals() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OfferApproval> offerApprovalList = null;
        try {
            offerApprovalList = session.createQuery("FROM OfferApproval", OfferApproval.class).list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return offerApprovalList;
    }

    @Override
    public void addAllOfferApprovals(List<OfferApproval> offerApprovalList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (OfferApproval offerApproval : offerApprovalList) {
                session.persist(offerApproval);
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
            session.createQuery("DELETE FROM OfferApproval").executeUpdate();
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
