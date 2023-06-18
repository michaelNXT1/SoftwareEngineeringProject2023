package DAOs;

import BusinessLayer.Member;
import Repositories.IMemberRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class MemberDAO implements IMemberRepository {

    public void addMember(Member member) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(member);
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

    public void removeMember(Member member) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(member);
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


    public Member getMember(String key) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Member member = null;
        try {
            member = session.get(Member.class, key);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return member;
    }

    @Override
    public List<Member> getAllMember() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Member> members = null;
        try {
            String hql = "FROM Member";
            Query<Member> query = session.createQuery(hql, Member.class);
            members = query.list();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return members;
    }



    @Override
    public void updateMember(Member member) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(member);
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


    public void clear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Member").executeUpdate();
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
