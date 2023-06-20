package DAOs;

import BusinessLayer.Discounts.Discount;
import Repositories.IDiscountRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DiscountDAO implements IDiscountRepo {
    @Override
    public void addDiscount(Discount discount) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(discount);
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
    public void removeDiscount(Discount discount) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(discount);
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
    public Discount get(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Discount discount = null;
        try {
            discount = session.get(Discount.class, id);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return discount;
    }

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM CategoryDiscount").executeUpdate();
            session.createQuery("DELETE FROM ProductDiscount").executeUpdate();
            session.createQuery("DELETE FROM StoreDiscount").executeUpdate();
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
    public List<Discount> getAllDiscounts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Discount> discounts = null;
        try {
            discounts = session.createQuery("FROM CategoryDiscount ", Discount.class).list();
            discounts.addAll(session.createQuery("FROM ProductDiscount", Discount.class).list());
            discounts.addAll(session.createQuery("FROM StoreDiscount", Discount.class).list());
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return discounts;
    }
}
