package DataAccessLayer.DAOs;

import BusinessLayer.Store;
import DataAccessLayer.DAOs.Interfaces.StoreDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class HibernateStoreDAO implements StoreDAO {
    private SessionFactory sessionFactory;

    public HibernateStoreDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Store getById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Store.class, id);
        }
    }

    @Override
    public List<Store> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Store> query = session.createQuery("FROM Store", Store.class);
            return query.list();
        }
    }

    // Implement other CRUD operations
}
