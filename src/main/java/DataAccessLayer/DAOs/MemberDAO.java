package DataAccessLayer.DAOs;

import BusinessLayer.Member;
import DataAccessLayer.DAOs.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MemberDAO {

    public void save(String key, Member member) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(member);
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

    public void delete(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Member member = session.get(Member.class, key);
            if (member != null) {
                session.delete(member);
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

    public Member getByKey(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Member member = null;
        try {
            member = session.get(Member.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return member;
    }
}

