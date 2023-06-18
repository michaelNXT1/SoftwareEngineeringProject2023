package DAOs;

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
            session.persist(discountPolicy);
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

    public boolean removeDiscountPolicy(BaseDiscountPolicy discountPolicy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(discountPolicy);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<BaseDiscountPolicy> getAllDiscountPolicies() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BaseDiscountPolicy> discountPolicies = null;
        try {
            discountPolicies = session.createQuery("FROM base_discount_policy", BaseDiscountPolicy.class).list();
        } catch (Exception e) {
            throw e;
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
            return session.get(BaseDiscountPolicy.class, policyId);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM DiscountPolicyOperation").executeUpdate();
            session.createQuery("DELETE FROM MaxQuantityDiscountPolicy").executeUpdate();
            session.createQuery("DELETE FROM MinBagTotalDiscountPolicy").executeUpdate();
            session.createQuery("DELETE FROM MinQuantityDiscountPolicy").executeUpdate();

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