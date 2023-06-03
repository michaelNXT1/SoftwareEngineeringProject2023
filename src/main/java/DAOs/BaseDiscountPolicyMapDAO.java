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
    public void addDiscountPolicy(Discount discount, IBaseDiscountPolicyRepository discountPolicy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(discount);
            session.saveOrUpdate(discountPolicy);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            this.discountPolicies.put(discount, discountPolicy);
        }
    }

    @Override
    public void removeDiscountPolicy(Discount discount) {
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
    public Map<Discount, IBaseDiscountPolicyRepository> getAllDiscountPolicies() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Map<Discount, IBaseDiscountPolicyRepository> discountPolicyMap = new HashMap<>();

        try {
            Query<Discount> query = session.createQuery("FROM Discount", Discount.class);
            List<Discount> discounts = query.list();

            for (Discount discount : discounts) {
                IBaseDiscountPolicyRepository discountPolicy = session.get(IBaseDiscountPolicyRepository.class, discount.getId());
                discountPolicyMap.put(discount, discountPolicy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return discountPolicyMap;
    }
}
