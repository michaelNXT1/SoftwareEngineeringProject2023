package DAOs;

import BusinessLayer.ShoppingBag;
import Repositories.IShoppingBagRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ShoppingBagDAO implements IShoppingBagRepository {

    @Override
    public void addShoppingBag(ShoppingBag shoppingBag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(shoppingBag);
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
    public void removeShoppingBag(ShoppingBag shoppingBag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(shoppingBag);
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
    public ShoppingBag getShoppingBagById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ShoppingBag shoppingBag = null;
        try {
            shoppingBag = session.get(ShoppingBag.class, id);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return shoppingBag;
    }

    @Override
    public List<ShoppingBag> getAllShoppingBags() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ShoppingBag> shoppingBags = null;
        try {
            shoppingBags = session.createQuery("FROM ShoppingBag", ShoppingBag.class).list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return shoppingBags;
    }

    @Override
    public void updateShoppingBag(ShoppingBag shoppingBag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(shoppingBag);
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

    public void clearShoppingBags() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM ShoppingBag").executeUpdate();
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


}
