package DAOs;

import BusinessLayer.Discounts.Discount;
import BusinessLayer.Policies.DiscountPolicies.BaseDiscountPolicy;
import Repositories.IBaseDiscountPolicyMapRepository;
import Repositories.IBaseDiscountPolicyRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDiscountPolicyMapDAO implements IBaseDiscountPolicyMapRepository {

    private Map<Discount, IBaseDiscountPolicyRepository> discountPolicies;

    public BaseDiscountPolicyMapDAO() {
        this.discountPolicies = new HashMap<>();
    }

    @Override
    public void addDiscountPolicy(BaseDiscountPolicy discount) {
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
            e.printStackTrace();
        } finally {
            session.close();
        }
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
            e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            session.close();
        }

        return discountPolicy;
    }

    @Override
    public BaseDiscountPolicy getDiscountPolicyById(Integer key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        BaseDiscountPolicy baseDiscountPolicy = null;
        try {
            baseDiscountPolicy = session.get(BaseDiscountPolicy.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return baseDiscountPolicy;
    }

    @Override
    public List<BaseDiscountPolicy> getAllDiscountPolicies() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BaseDiscountPolicy> discountPolicyMap = null;
        try {
            Query<BaseDiscountPolicy> query = session.createQuery("FROM DiscountPolicyOperation", BaseDiscountPolicy.class);
            discountPolicyMap = query.list();
            discountPolicyMap.addAll( session.createQuery("FROM MaxQuantityDiscountPolicy", BaseDiscountPolicy.class).list());
            discountPolicyMap.addAll( session.createQuery("FROM MinBagTotalDiscountPolicy", BaseDiscountPolicy.class).list());
            discountPolicyMap.addAll( session.createQuery("FROM MinQuantityDiscountPolicy", BaseDiscountPolicy.class).list());
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
