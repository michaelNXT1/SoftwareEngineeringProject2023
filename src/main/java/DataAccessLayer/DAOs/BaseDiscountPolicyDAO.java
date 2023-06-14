package DataAccessLayer.DAOs;

import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BaseDiscountPolicyDAO {
    public void saveOrUpdate(BaseDiscountPolicy discountPolicy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(discountPolicy);
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

    public void delete(BaseDiscountPolicy discountPolicy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(discountPolicy);
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

    public List<BaseDiscountPolicy> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BaseDiscountPolicy> discountPolicies = null;
        try {
            discountPolicies = session.createQuery("FROM BaseDiscountPolicy", BaseDiscountPolicy.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return discountPolicies;
    }
}
