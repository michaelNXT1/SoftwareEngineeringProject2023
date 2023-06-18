package DAOs;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.DiscountPolicyOperation;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MaxQuantityDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MinBagTotalDiscountPolicy;
import BusinessLayer.Policies.DiscountPolicies.PolicyTypes.MinQuantityDiscountPolicy;
import Repositories.IBaseDiscountPolicyMapRepository;
import Repositories.IBaseDiscountPolicyRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDiscountPolicyMapDAO implements IBaseDiscountPolicyMapRepository {

    private Map<Discount, IBaseDiscountPolicyRepository> discountPolicies;

    public BaseDiscountPolicyMapDAO() {
        this.discountPolicies = new HashMap<>();
    }

    @Override
    public int addDiscountPolicy(BaseDiscountPolicy discount) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(discount);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return discount.getPolicyId();
    }

    @Override
    public void removeDiscountPolicy(BaseDiscountPolicy discount) {
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
            throw e;
        } finally {
            session.close();
            this.discountPolicies.remove(discount);
        }
    }

    @Override
    public BaseDiscountPolicy getDiscountPolicy(Discount discount) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        BaseDiscountPolicy discountPolicy = null;
        try {
            discountPolicy = session.get(BaseDiscountPolicy.class, discount.getId());
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }

        return discountPolicy;
    }

    @Override
    public void updateBaseDiscountPolicy(BaseDiscountPolicy baseDiscountPolicy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(baseDiscountPolicy);
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
    public BaseDiscountPolicy getDiscountPolicyById(Integer key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BaseDiscountPolicy> positions = null;
        try {
            Query<BaseDiscountPolicy> query = session.createQuery("FROM MinBagTotalDiscountPolicy s WHERE s.policyId = :key", BaseDiscountPolicy.class);
            query.setParameter("key", key);
            positions = query.list();

            query = session.createQuery("FROM MinQuantityDiscountPolicy s WHERE s.policyId = :key", BaseDiscountPolicy.class);
            query.setParameter("key", key);
            positions.addAll(query.list());

            query = session.createQuery("FROM MaxQuantityDiscountPolicy s WHERE s.policyId = :key", BaseDiscountPolicy.class);
            query.setParameter("key", key);
            positions.addAll(query.list());
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return positions.stream().findFirst().orElse(null);
    }

    @Override
    public List<BaseDiscountPolicy> getAllDiscountPolicies() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BaseDiscountPolicy> discountPolicyMap = new ArrayList<>();
        try {
            discountPolicyMap.addAll( session.createQuery("FROM MaxQuantityDiscountPolicy", MaxQuantityDiscountPolicy.class).list());
            discountPolicyMap.addAll( session.createQuery("FROM MinBagTotalDiscountPolicy", MinBagTotalDiscountPolicy.class).list());
            discountPolicyMap.addAll( session.createQuery("FROM MinQuantityDiscountPolicy", MinQuantityDiscountPolicy.class).list());
            discountPolicyMap.addAll(session.createQuery("FROM DiscountPolicyOperation", DiscountPolicyOperation.class).list());
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }

        return discountPolicyMap;
    }

    @Override
    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM MaxQuantityDiscountPolicy").executeUpdate();
            session.createQuery("DELETE FROM MinBagTotalDiscountPolicy").executeUpdate();
            session.createQuery("DELETE FROM MinQuantityDiscountPolicy").executeUpdate();
            session.createQuery("DELETE FROM DiscountPolicyOperation").executeUpdate();
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
