package DAOs;

import BusinessLayer.Position;
import Repositories.IPositionRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Position> getAllPositions() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Position> positions = null;
        try {
            positions = session.createQuery("FROM positions", Position.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return positions;
    }

    // Add other methods as needed
}