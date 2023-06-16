package DAOs;

import BusinessLayer.Position;
import Repositories.IPositionRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PositionDAO implements IPositionRepository {

    public void addPosition(Position position) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(position);
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

    public void removePosition(Position position) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(position);
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

    public List<Position> getAllPositions() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Position> positions = null;
        try {
            positions = session.createQuery("FROM StoreOwner", Position.class).list();
            positions.addAll(session.createQuery("FROM StoreManager", Position.class).list());
            positions.addAll(session.createQuery("FROM StoreFounder", Position.class).list());
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return positions;
    }

    public List<Position> getPositionsByMember(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Position> positions = null;
        try {
            Query<Position> query = session.createQuery("FROM StoreOwner s WHERE s.positionMember.username = :username", Position.class);
            query.setParameter("username", username);
            positions = query.list();

            query = session.createQuery("FROM StoreManager s WHERE s.positionMember.username = :username", Position.class);
            query.setParameter("username", username);
            positions.addAll(query.list());

            query = session.createQuery("FROM StoreFounder s WHERE s.positionMember.username = :username", Position.class);
            query.setParameter("username", username);
            positions.addAll(query.list());
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return positions;
    }


    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM StoreOwner").executeUpdate();
            session.createQuery("DELETE FROM StoreManager").executeUpdate();
            session.createQuery("DELETE FROM StoreFounder").executeUpdate();
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
    // Add other methods as needed
}