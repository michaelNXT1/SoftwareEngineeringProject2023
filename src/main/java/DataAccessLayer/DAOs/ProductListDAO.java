package DataAccessLayer.DAOs;

import BusinessLayer.Repositories.ProductListRepository;
import Utils.Pair;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListDAO {
    public void saveOrUpdate(int productId, int quantity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Pair productList = new Pair<>(productId, quantity);
            session.saveOrUpdate(productList);
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

    public void delete(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Pair productList = session.get(Pair.class, productId);
            if (productList != null) {
                session.delete(productList);
            }
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

    public Pair getByProductId(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Pair productList = null;
        try {
            productList = session.get(Pair.class, productId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productList;
    }

    public Map<Integer, Integer> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Pair> productLists = null;
        try {
            productLists = session.createQuery("FROM ProductList", Pair.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        Map<Integer, Integer> productListMap = new HashMap<>();
        if (productLists != null) {
            for (Pair pair : productLists) {
                productListMap.put((Integer) pair.getFirst(), (Integer) pair.getSecond());
            }
        }
        return productListMap;
    }
}
