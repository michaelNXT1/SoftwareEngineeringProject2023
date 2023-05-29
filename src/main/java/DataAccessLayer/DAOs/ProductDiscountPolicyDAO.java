package DataAccessLayer.DAOs;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDiscountPolicyDAO {
    public void saveOrUpdate(Discount discount, List<BaseDiscountPolicy> discountPolicies) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(discount);
            for (BaseDiscountPolicy policy : discountPolicies) {
                session.saveOrUpdate(policy);
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

    public void delete(Discount discount) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(discount);
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

    public Map<Discount, List<BaseDiscountPolicy>> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Map<Discount, List<BaseDiscountPolicy>> productDiscountPolicyMap = null;
        try {
            List<Discount> discounts = session.createQuery("FROM Discount", Discount.class).list();
            productDiscountPolicyMap = new HashMap<>();
            for (Discount discount : discounts) {
                List<BaseDiscountPolicy> discountPolicies = session
                        .createQuery("FROM BaseDiscountPolicy WHERE discount = :discount", BaseDiscountPolicy.class)
                        .setParameter("discount", discount)
                        .list();
                productDiscountPolicyMap.put(discount, discountPolicies);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productDiscountPolicyMap;
    }
}
