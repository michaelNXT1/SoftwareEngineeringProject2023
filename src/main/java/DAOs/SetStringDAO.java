package DAOs;

import BusinessLayer.Store;
import BusinessLayer.Repositories.IStringSetRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetStringDAO implements IStringSetRepository {

    @Override
    public void addString(String string) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(string);
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

    @Override
    public void removeString(String string) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(string);
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

    @Override
    public Set<String> getAllStrings() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Set<String> stringSet = new HashSet<>();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Store> query = builder.createQuery(Store.class);
            Root<Store> root = query.from(Store.class);
            query.select(root).distinct(true);
            List<Store> stores = session.createQuery(query).getResultList();
            for (Store store : stores) {
                IStringSetRepository categories = store.getCategories();
                stringSet.addAll(categories.getAllStrings());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return stringSet;
    }

}

