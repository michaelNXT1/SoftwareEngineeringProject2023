package DAOs;

import DataAccessLayer.DAOs.HibernateUtil;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import Repositories.IBaseDiscountPolicyRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BaseDiscountPolicyDAO implements IBaseDiscountPolicyRepository {
    public void addDiscountPolicy(BaseDiscountPolicy discountPolicy) {
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

    public boolean removeDiscountPolicy(BaseDiscountPolicy discountPolicy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(discountPolicy);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public List<BaseDiscountPolicy> getAllDiscountPolicies() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BaseDiscountPolicy> discountPolicies = null;
        try {
            discountPolicies = session.createQuery("FROM base_discount_policy", BaseDiscountPolicy.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return discountPolicies;
    }

    @Override
    public BaseDiscountPolicy getDiscountPolicyById(int policyId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // Retrieve the discount policy by policyId
            BaseDiscountPolicy discountPolicy = session.get(BaseDiscountPolicy.class, policyId);
            return discountPolicy;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}