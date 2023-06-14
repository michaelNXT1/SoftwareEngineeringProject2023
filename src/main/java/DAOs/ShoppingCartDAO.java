package DAOs;

import BusinessLayer.Purchase;
import BusinessLayer.ShoppingBag;
import BusinessLayer.ShoppingCart;
import Repositories.IShoppingCartRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ShoppingCartDAO implements IShoppingCartRepo {
    @Override
    public void addShoppingCart(ShoppingCart shoppingCart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(shoppingCart);
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
    public void removeShoppingBag(ShoppingCart shoppingCart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(shoppingCart);
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
    public ShoppingCart getShoppingBagById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ShoppingCart shoppingCart = null;
        try {
            shoppingCart = session.get(ShoppingCart.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return shoppingCart;
    }

    @Override
    public List<ShoppingCart> getAllShoppingCart() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ShoppingCart> shoppingBags = null;
        try {
            shoppingBags = session.createQuery("FROM ShoppingCart", ShoppingCart.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return shoppingBags;
    }

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM ShoppingCart").executeUpdate();
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
